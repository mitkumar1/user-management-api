# Senior Developer Interview Summary: Issue, Impact, Analysis, Solution, Diagram & Documentation

## Problem 1: Large File Uploads Failing (>5GB)
**Issue & Impact:**
Compliance officers were unable to upload regulatory files larger than 5GB, blocking month-end submissions and risking regulatory penalties. The application was running out of memory and S3 single PUT requests were timing out, affecting 50+ users and critical deadlines.

**Diagram: Issue & Solution Flow**
```
+-------------------+
| User Uploads File |
+-------------------+
         |
         v
+-----------------------------+
| App loads file in memory    |
+-----------------------------+
         |
         v
+-----------------------------+
| OutOfMemoryError, Timeout   |
+-----------------------------+
         |
         v
+-----------------------------+
| S3 PUT fails (>5GB limit)   |
+-----------------------------+
         |
         v
+-----------------------------+
| Refactor: Multipart Upload  |
+-----------------------------+
         |
         v
+-----------------------------+
| Chunked Upload (100MB)      |
+-----------------------------+
         |
         v
+-----------------------------+
| Progress Tracking, Success  |
+-----------------------------+
         |
         v
+-----------------------------+
| Modern Approach: Parallel Chunk Uploads |
+-----------------------------+
         |
         v
+-----------------------------+
| ExecutorService (Java 17)   |
+-----------------------------+
         |
         v
+-----------------------------+
| Spring Scheduler Monitoring |
+-----------------------------+
```

**Analysis:**
- Investigated application logs and found OutOfMemoryError due to loading entire files into memory.
- Reviewed AWS S3 documentation and identified the 5GB limit for single PUT operations.
- Confirmed that the current implementation did not support chunked uploads or progress tracking.

**Solution & Code:**
- Refactored the upload service to use AWS S3 multipart (chunked) upload, processing files in 100MB chunks to avoid memory issues.
- Added progress tracking and error handling for reliability.
- Validated the solution with test uploads up to 10GB.
- **Modern Java 17 Approach:** Used ExecutorService (virtual threads) for parallel chunk uploads and Spring Scheduler for monitoring.

```java
// Optimized S3 multipart upload
public String uploadLargeFileToS3(MultipartFile file, String bucketName) {
    String fileName = file.getOriginalFilename();
    InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, fileName);
    String uploadId = s3Client.initiateMultipartUpload(initRequest).getUploadId();
    List<PartETag> partETags = new ArrayList<>();
    try (InputStream inputStream = file.getInputStream()) {
        long totalSize = file.getSize();
        long uploaded = 0;
        int partNumber = 1;
        while (uploaded < totalSize) {
            long chunkSize = Math.min(100 * 1024 * 1024, totalSize - uploaded);
            byte[] chunkData = new byte[(int) chunkSize];
            int bytesRead = inputStream.read(chunkData);
            if (bytesRead > 0) {
                UploadPartRequest uploadRequest = new UploadPartRequest()
                    .withBucketName(bucketName)
                    .withKey(fileName)
                    .withUploadId(uploadId)
                    .withPartNumber(partNumber)
                    .withInputStream(new ByteArrayInputStream(chunkData, 0, bytesRead))
                    .withPartSize(bytesRead);
                UploadPartResult uploadResult = s3Client.uploadPart(uploadRequest);
                partETags.add(uploadResult.getPartETag());
                uploaded += bytesRead;
                partNumber++;
            }
        }
        CompleteMultipartUploadRequest completeRequest = new CompleteMultipartUploadRequest(bucketName, fileName, uploadId, partETags);
        CompleteMultipartUploadResult result = s3Client.completeMultipartUpload(completeRequest);
        return result.getETag();
    }
}
```

```java
// Modern S3 multipart upload with Java 17 virtual threads and ExecutorService
public String uploadLargeFileToS3(MultipartFile file, String bucketName) throws Exception {
    String fileName = file.getOriginalFilename();
    InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, fileName);
    String uploadId = s3Client.initiateMultipartUpload(initRequest).getUploadId();
    List<PartETag> partETags = Collections.synchronizedList(new ArrayList<>());
    int chunkSize = 100 * 1024 * 1024; // 100MB

    try (InputStream inputStream = file.getInputStream();
         ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

        long totalSize = file.getSize();
        int partNumber = 1;
        List<Future<PartETag>> futures = new ArrayList<>();

        while (inputStream.available() > 0) {
            byte[] chunkData = inputStream.readNBytes(chunkSize);
            int currentPart = partNumber++;
            futures.add(executor.submit(() -> {
                UploadPartRequest uploadRequest = new UploadPartRequest()
                    .withBucketName(bucketName)
                    .withKey(fileName)
                    .withUploadId(uploadId)
                    .withPartNumber(currentPart)
                    .withInputStream(new ByteArrayInputStream(chunkData))
                    .withPartSize(chunkData.length);
                UploadPartResult uploadResult = s3Client.uploadPart(uploadRequest);
                return uploadResult.getPartETag();
            }));
        }

        for (Future<PartETag> future : futures) {
            partETags.add(future.get()); // Wait for all chunks to finish
        }

        CompleteMultipartUploadRequest completeRequest =
            new CompleteMultipartUploadRequest(bucketName, fileName, uploadId, partETags);
        CompleteMultipartUploadResult result = s3Client.completeMultipartUpload(completeRequest);
        return result.getETag();
    }
}
```

**Testing & Verification:**
I understand we implemented the chunked upload method to extend support for files up to 10GB. All fixes were thoroughly tested in local, staging, and production environments to ensure our logic works properly, and we were able to successfully upload files up to 10GB without issues.

**Step-by-Step Testing Process:**
1. **Local Build & Setup:**
   - Built the project using Maven:
     ```bash
     mvn clean install
     ```
   - Started the Spring Boot application locally:
     ```bash
     java -jar target/user-management-api-1.0.0-SNAPSHOT.jar
     ```
2. **Prepare Test File:**
   - Generated a sample 10GB file for testing:
     ```bash
     fsutil file createnew test-large-file-10gb.dat 10737418240
     # Or use a script to concatenate smaller files
     ```
3. **UI Upload Test:**
   - Opened the web application in a browser (e.g., http://localhost:4200 or the deployed UI endpoint).
   - Navigated to the file upload section/form.
   - Selected the 10GB test file using the file picker.
   - Chose the target S3 bucket (if applicable).
   - Clicked the 'Upload' button to start the upload process.
   - Observed the UI for progress bar updates, percentage completion, and status messages.
   - Waited for the upload to complete and checked for a success message or ETag confirmation in the UI.
4. **Validation:**
   - Verified in the UI that the file appeared in the uploaded files list (if available).
   - Checked the S3 bucket directly to confirm the file was present and the ETag matched.
   - Ensured no OutOfMemoryError or timeouts occurred during upload (monitored logs if needed).
   - Repeated the test with files of varying sizes (5GB, 8GB, 10GB) to ensure robustness.
5. **Staging & Production:**
   - Deployed the fix to staging and production environments.
   - Performed similar uploads via the UI and confirmed successful completion and system stability.

**Why ExecutorService and Spring Scheduler?**
- **ExecutorService (virtual threads):** Enables parallel chunk uploads, improving speed and resource efficiency. Virtual threads are lightweight, so you can run many concurrent uploads without exhausting system resources.
- **Spring Scheduler:** Enables periodic monitoring of upload progress, retrying failed chunks, and cleaning up incomplete uploads automatically.

```java
// Example: Spring Scheduler for Monitoring
@Component
public class S3UploadMonitor {
    @Scheduled(fixedRate = 30000)
    public void monitorActiveUploads() {
        // Check progress, retry failed chunks, cleanup incomplete uploads
        // ...implementation...
    }
}
```

**Deployment:**
- Created a feature branch and committed code changes.
- Performed local and staging environment testing.
- Coordinated with DevOps for hotfix deployment to production before the regulatory deadline.
- Monitored uploads post-deployment to ensure stability.

**Documentation:**
- Documented the issue, solution, and deployment steps in a Confluence page for team knowledge sharing and future reference.
- Included code samples, architecture diagrams, and troubleshooting steps.

---

## Problem 2: SSL Certificate Expiration
**Issue & Impact:**
All external API calls failed due to an expired client SSL certificate, causing a complete outage and blocking regulatory reports for 500+ compliance officers. This risked compliance violations and potential financial penalties.

**Diagram: Issue & Solution Flow**
```
+-------------------+
| API Call (SSL)    |
+-------------------+
         |
         v
+-----------------------------+
| SSLHandshakeException       |
+-----------------------------+
         |
         v
+-----------------------------+
| Certificate Expired         |
+-----------------------------+
         |
         v
+-----------------------------+
| Renew Certificate           |
+-----------------------------+
         |
         v
+-----------------------------+
| Install & Restart Services  |
+-----------------------------+
         |
         v
+-----------------------------+
| API Calls Restored          |
+-----------------------------+
         |
         v
+-----------------------------+
| Add Monitoring/Alerts       |
+-----------------------------+
```

**Analysis:**
- Monitored application logs and identified SSLHandshakeException errors.
- Used OpenSSL to inspect the client certificate and confirmed expiration.
- Discovered that the auto-renewal process had failed and no monitoring was in place.

**Solution & Code:**
- Coordinated with the security team to urgently renew the SSL certificate.
- Installed the new certificate on all servers and restarted services to restore API connectivity.
- Implemented daily automated certificate expiration monitoring and multi-channel alerts (Slack, email) to prevent future outages.

```java
// Certificate expiration monitor (Spring Scheduler)
@Scheduled(cron = "0 0 8 * * *")
public void checkCertificateExpiration() {
    for (String certPath : certificatePaths) {
        X509Certificate cert = loadCertificate(certPath);
        Date expirationDate = cert.getNotAfter();
        long daysUntilExpiration = ChronoUnit.DAYS.between(LocalDate.now(), expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        if (daysUntilExpiration <= 7) {
            alertService.sendCriticalAlert("SSL Certificate expires in " + daysUntilExpiration + " days: " + certPath);
        }
    }
}
```

**Deployment:**
- Created an emergency branch for the monitoring solution.
- Tested the fix in staging and deployed to production with change management approval.
- Verified restoration of all external API calls and user access.

**Documentation:**
- Updated the Confluence page with the incident timeline, root cause analysis, solution, and monitoring setup.
- Added SSL troubleshooting steps and certificate renewal procedures for future reference.

---

## Problem 3: Oracle DB Performance Issue
**Issue & Impact:**
A critical business report was taking over 30 minutes to generate, impacting end-of-day reconciliation and delaying downstream processes. The slow query execution was affecting 100+ users and causing missed SLAs.

**How I Reproduced & Investigated:**
1. **User Complaint:**
   - Received reports from business users about slow report generation.
2. **Monitoring & Logging:**
   - Used Splunk to search for slow query logs and error patterns.
   - Used Dynatrace to trace the transaction and identify the bottleneck at the database layer.
3. **Local Reproduction:**
   - Ran the problematic query in a local development environment against a copy of production data.
   - Confirmed the query was slow and matched the production behavior.

**Step-by-Step Analysis and Resolution After Log Identification:**
1. **Root Cause Diagnosis:**
   - After identifying the slow query in Splunk/Dynatrace, extracted the SQL statement:
     ```sql
     SELECT * FROM transactions WHERE transaction_date = TO_DATE('2025-09-29', 'YYYY-MM-DD');
     ```
   - Noted that the query was performing a full table scan, causing high I/O and long execution time.

2. **EXPLAIN PLAN Usage:**
   - Ran EXPLAIN PLAN in Oracle to analyze the query execution path:
     ```sql
     EXPLAIN PLAN FOR
     SELECT * FROM transactions WHERE transaction_date = TO_DATE('2025-09-29', 'YYYY-MM-DD');
     SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY);
     ```
   - The plan showed a full table scan (TABLE ACCESS FULL) on 'transactions'.
   - Identified missing index on 'transaction_date'.

3. **Fix: Index Creation and Query Refactoring:**
   - Created an index to optimize the query:
     ```sql
     CREATE INDEX idx_transaction_date ON transactions(transaction_date);
     ```
   - Refactored the query to use indexed columns and avoid unnecessary functions:
     ```sql
     -- Before (less efficient)
     SELECT * FROM transactions WHERE TO_CHAR(transaction_date, 'YYYY-MM-DD') = '2025-09-29';
     -- After (efficient, uses index)
     SELECT * FROM transactions WHERE transaction_date = TO_DATE('2025-09-29', 'YYYY-MM-DD');
     ```

4. **Local Testing Steps:**
   - Connected to local Oracle DB with production-like data.
   - Ran the original query and measured execution time (e.g., 30+ minutes).
   - Applied the index and refactored query.
   - Ran the optimized query and confirmed execution time dropped to seconds.
   - Used EXPLAIN PLAN again to verify index usage (TABLE ACCESS BY INDEX ROWID).
   - Checked for correct results and no errors.

5. **Staging and Production Deployment:**
   - Deployed index and query changes to staging.
   - Monitored query performance using Splunk/Dynatrace.
   - Scheduled a maintenance window for production deployment.
   - Applied index in production and validated with real user transactions.
   - Confirmed report generation met SLA and no negative impact on other queries.

6. **Documentation in Confluence:**
   - Documented the issue, root cause, EXPLAIN PLAN output, SQL scripts, and before/after performance metrics.
   - Added screenshots of Splunk/Dynatrace traces, EXPLAIN PLAN results, and test validations.
   - Provided rollback steps and index impact analysis for future reference.
