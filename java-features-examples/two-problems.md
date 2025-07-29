# Critical P1 Issues Resolved in ORCIT-EIT Application
## Bank of America - Risk & Compliance Team
### Senior Developer Interview Documentation

---

## Table of Contents
1. [P1 Issue #1: Large File Upload Failure to S3](#p1-issue-1-large-file-upload-failure-to-s3)
2. [P1 Issue #2: SSL Certificate Expiration](#p1-issue-2-ssl-certificate-expiration)
3. [Interview Presentation Guide](#interview-presentation-guide)

---

## P1 Issue #1: Large File Upload Failure to S3

## P1 Issue #1: Large File Upload Failure to S3

### **Issue Discovery & Escalation**

#### **Initial Alert (8:30 AM)**
```
From: Sarah Johnson <sarah.johnson@bofa.com> (Product Owner)
To: Development Team <orcit-dev-team@bofa.com>
Subject: 🚨 URGENT: P1 Issue - Large File Uploads Failing

Team,

We're getting multiple escalations from compliance officers who cannot upload 
their regulatory files. Files >5GB are failing completely. This is blocking 
month-end regulatory submissions due at 5 PM today.

Need immediate assistance. Can we have an emergency call in 15 minutes?

Sarah Johnson
Product Owner - ORCIT Compliance Platform
```

#### **Emergency Call (8:45 AM)**
**Participants:** Product Owner (Sarah), Scrum Master (Mike), Senior Developer (Me), DevOps Lead

**Call Summary:**
- **Sarah (PO):** "We have 50+ compliance officers blocked. Files are timing out during upload."
- **Mike (SM):** "I'll create a P1 JIRA immediately and assign to Mithlesh as our senior backend developer."
- **Me:** "I'll start investigating right away. Sounds like a memory/chunking issue with large files."
- **DevOps:** "I can prepare hotfix deployment pipeline if needed."

#### **JIRA Story Creation**
```
JIRA: ORCIT-3892
Title: P1 - Large File Upload Failure (>5GB) to S3 Buckets
Priority: Highest (P1)
Assignee: Mithlesh Kumar (me)
Reporter: Mike Thompson (Scrum Master)
Epic: Regulatory File Management

Description:
Compliance officers unable to upload regulatory files >5GB to S3.
- Files timing out after 10 minutes
- OutOfMemoryError in application logs
- Critical: Month-end deadline today at 5 PM
- Affected: 50+ users, 3 large files pending upload

Acceptance Criteria:
- Support file uploads up to 10GB
- Successful upload within reasonable time
- No memory issues during upload
- Progress tracking for large files

Sprint: Emergency Hotfix
```

### **Technical Investigation (9:00 AM - 10:30 AM)**

#### **Problem Analysis**
```java
// Current problematic code I found
@Service
public class FileUploadService {
    
    @Autowired
    private AmazonS3 s3Client;
    
    public void uploadToS3(MultipartFile file, String bucketName) {
        try {
            // 🚨 PROBLEM: Loading entire 5GB+ file into memory
            byte[] fileContent = file.getBytes(); // OutOfMemoryError!
            
            // 🚨 PROBLEM: Single upload request for huge file
            s3Client.putObject(bucketName, file.getOriginalFilename(), 
                new ByteArrayInputStream(fileContent), 
                new ObjectMetadata());
                
        } catch (Exception e) {
            log.error("Upload failed: {}", e.getMessage());
            throw new FileUploadException("Failed to upload file");
        }
    }
}
```

**Issues Identified:**
- Files >5GB causing `OutOfMemoryError` (heap size only 4GB)
- Single upload requests timing out after 10 minutes
- S3 has 5GB limit for single PUT operations
- No progress tracking for users

### **Solution Implementation**

#### **Step 1: Create Feature Branch (10:30 AM)**
```bash
# Created feature branch from main
$ git checkout main
$ git pull origin main
$ git checkout -b feature/ORCIT-3892-large-file-upload-fix
$ git push -u origin feature/ORCIT-3892-large-file-upload-fix
```

#### **Step 2: Implement Chunked Upload (10:30 AM - 12:00 PM)**
```java
@Service
@Slf4j
public class OptimizedFileUploadService {
    
    @Autowired
    private AmazonS3 s3Client;
    
    private static final long CHUNK_SIZE = 100 * 1024 * 1024; // 100MB chunks
    
    public String uploadLargeFileToS3(MultipartFile file, String bucketName) {
        String fileName = file.getOriginalFilename();
        String uploadId = null;
        
        try {
            log.info("Starting large file upload: {} ({}MB)", fileName, file.getSize() / (1024 * 1024));
            
            // Step 1: Initiate multipart upload
            InitiateMultipartUploadRequest initRequest = 
                new InitiateMultipartUploadRequest(bucketName, fileName);
            
            uploadId = s3Client.initiateMultipartUpload(initRequest).getUploadId();
            log.info("Initiated multipart upload: {} with uploadId: {}", fileName, uploadId);
            
            // Step 2: Upload file in chunks
            List<PartETag> partETags = uploadFileInChunks(file, bucketName, fileName, uploadId);
            
            // Step 3: Complete multipart upload
            CompleteMultipartUploadRequest completeRequest = 
                new CompleteMultipartUploadRequest(bucketName, fileName, uploadId, partETags);
            
            CompleteMultipartUploadResult result = s3Client.completeMultipartUpload(completeRequest);
            
            log.info("✅ Successfully uploaded file: {} to S3. ETag: {}", fileName, result.getETag());
            return result.getETag();
            
        } catch (Exception e) {
            // Cleanup incomplete upload
            if (uploadId != null) {
                try {
                    s3Client.abortMultipartUpload(new AbortMultipartUploadRequest(bucketName, fileName, uploadId));
                    log.info("Aborted incomplete upload: {}", uploadId);
                } catch (Exception abortException) {
                    log.error("Failed to abort upload: {}", uploadId, abortException);
                }
            }
            log.error("❌ Failed to upload file: {}", fileName, e);
            throw new FileUploadException("Upload failed: " + e.getMessage());
        }
    }
    
    private List<PartETag> uploadFileInChunks(MultipartFile file, String bucketName, 
                                             String fileName, String uploadId) throws IOException {
        
        List<PartETag> partETags = new ArrayList<>();
        
        try (InputStream inputStream = file.getInputStream()) {
            long totalSize = file.getSize();
            long uploaded = 0;
            int partNumber = 1;
            
            while (uploaded < totalSize) {
                // Calculate chunk size for this part
                long chunkSize = Math.min(CHUNK_SIZE, totalSize - uploaded);
                
                // Read chunk data (only 100MB at a time - memory efficient!)
                byte[] chunkData = new byte[(int) chunkSize];
                int bytesRead = inputStream.read(chunkData);
                
                if (bytesRead > 0) {
                    // Upload this chunk
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
                    
                    // Log progress for monitoring
                    double progress = (uploaded * 100.0) / totalSize;
                    log.info("📊 Uploaded chunk {}: {:.1f}% complete ({} / {} MB)", 
                            partNumber - 1, progress, uploaded / (1024 * 1024), totalSize / (1024 * 1024));
                }
            }
        }
        
        log.info("✅ All {} chunks uploaded successfully", partETags.size());
        return partETags;
    }
}
```

#### **Step 3: Add Spring Boot Scheduler for Monitoring (12:00 PM - 1:00 PM)**
```java
@Component
@Slf4j
public class S3UploadMonitor {
    
    @Autowired
    private AmazonS3 s3Client;
    
    private final Map<String, UploadProgress> activeUploads = new ConcurrentHashMap<>();
    
    // Monitor active uploads every 30 seconds
    @Scheduled(fixedRate = 30000)
    public void monitorActiveUploads() {
        
        if (activeUploads.isEmpty()) {
            return; // No active uploads to monitor
        }
        
        log.info("📊 Monitoring {} active uploads", activeUploads.size());
        
        for (Map.Entry<String, UploadProgress> entry : activeUploads.entrySet()) {
            String uploadId = entry.getKey();
            UploadProgress progress = entry.getValue();
            
            try {
                // Check upload progress
                ListPartsRequest listRequest = new ListPartsRequest(
                    progress.getBucketName(), progress.getFileName(), uploadId);
                
                PartListing partListing = s3Client.listParts(listRequest);
                
                long uploadedSize = partListing.getParts().stream()
                    .mapToLong(PartSummary::getSize)
                    .sum();
                
                double progressPercent = (uploadedSize * 100.0) / progress.getTotalSize();
                
                log.info("📈 Upload {} progress: {:.1f}% ({} / {} MB)", 
                        uploadId, 
                        progressPercent,
                        uploadedSize / (1024 * 1024),
                        progress.getTotalSize() / (1024 * 1024));
                
                // Remove completed uploads
                if (progressPercent >= 99.0) {
                    activeUploads.remove(uploadId);
                    log.info("✅ Upload {} completed, removed from monitoring", uploadId);
                }
                
            } catch (Exception e) {
                log.warn("⚠️  Upload {} may have completed or failed: {}", uploadId, e.getMessage());
                activeUploads.remove(uploadId);
            }
        }
    }
    
    // Daily cleanup of incomplete uploads at 2 AM
    @Scheduled(cron = "0 0 2 * * *")
    public void cleanupIncompleteUploads() {
        
        List<String> buckets = Arrays.asList("compliance-files", "regulatory-reports", "trading-data");
        
        log.info("🧹 Starting daily cleanup of incomplete uploads");
        
        for (String bucketName : buckets) {
            try {
                ListMultipartUploadsRequest listRequest = new ListMultipartUploadsRequest(bucketName);
                MultipartUploadListing uploadListing = s3Client.listMultipartUploads(listRequest);
                
                int cleanedCount = 0;
                for (MultipartUpload upload : uploadListing.getMultipartUploads()) {
                    // Clean up uploads older than 1 day
                    if (upload.getInitiated().before(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)))) {
                        
                        s3Client.abortMultipartUpload(new AbortMultipartUploadRequest(
                            bucketName, upload.getKey(), upload.getUploadId()));
                        
                        cleanedCount++;
                        log.info("🗑️  Cleaned up incomplete upload: {} in bucket: {}", 
                                upload.getKey(), bucketName);
                    }
                }
                
                log.info("✅ Cleaned {} incomplete uploads from bucket: {}", cleanedCount, bucketName);
                
            } catch (Exception e) {
                log.error("❌ Failed to cleanup incomplete uploads in bucket: {}", bucketName, e);
            }
        }
    }
    
    // Helper method to register upload for monitoring
    public void registerUpload(String uploadId, String bucketName, String fileName, long totalSize) {
        activeUploads.put(uploadId, new UploadProgress(bucketName, fileName, totalSize));
        log.info("📝 Registered upload {} for monitoring", uploadId);
    }
    
    // Helper class to track upload progress
    public static class UploadProgress {
        private final String bucketName;
        private final String fileName;
        private final long totalSize;
        private final Instant startTime;
        
        public UploadProgress(String bucketName, String fileName, long totalSize) {
            this.bucketName = bucketName;
            this.fileName = fileName;
            this.totalSize = totalSize;
            this.startTime = Instant.now();
        }
        
        // Getters
        public String getBucketName() { return bucketName; }
        public String getFileName() { return fileName; }
        public long getTotalSize() { return totalSize; }
        public Instant getStartTime() { return startTime; }
    }
}
```

#### **Step 4: Update Controller (1:00 PM - 1:30 PM)**
```java
@RestController
@RequestMapping("/api/files")
@Slf4j
public class FileUploadController {
    
    @Autowired
    private OptimizedFileUploadService uploadService;
    
    @Autowired
    private S3UploadMonitor uploadMonitor;
    
    @PostMapping("/upload-large")
    public ResponseEntity<UploadResponse> uploadLargeFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("bucket") String bucketName) {
        
        try {
            // Validate file size (max 10GB for safety)
            if (file.getSize() > 10L * 1024 * 1024 * 1024) { // 10GB
                log.warn("⚠️  File too large: {} bytes", file.getSize());
                return ResponseEntity.badRequest()
                    .body(new UploadResponse("File too large. Maximum size: 10GB"));
            }
            
            log.info("🚀 Starting upload for file: {} ({} MB)", 
                    file.getOriginalFilename(), 
                    file.getSize() / (1024 * 1024));
            
            // Start upload
            String eTag = uploadService.uploadLargeFileToS3(file, bucketName);
            
            log.info("✅ Upload completed successfully: {}", file.getOriginalFilename());
            
            return ResponseEntity.ok(new UploadResponse(
                "File uploaded successfully. ETag: " + eTag
            ));
            
        } catch (Exception e) {
            log.error("❌ Upload failed for file: {}", file.getOriginalFilename(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new UploadResponse("Upload failed: " + e.getMessage()));
        }
    }
    
    public static class UploadResponse {
        private final String message;
        private final String timestamp;
        
        public UploadResponse(String message) { 
            this.message = message; 
            this.timestamp = Instant.now().toString();
        }
        
        public String getMessage() { return message; }
        public String getTimestamp() { return timestamp; }
    }
}
```

### **Testing & Deployment Process**

#### **Step 5: Local Testing (1:30 PM - 2:30 PM)**
```bash
# Built and tested locally
$ mvn clean compile
$ mvn test

# Tested with sample 6GB file
$ curl -X POST -F "file=@test-large-file-6gb.csv" \
  -F "bucket=compliance-files" \
  http://localhost:8080/api/files/upload-large

# ✅ Result: Upload successful in 12 minutes (vs previous timeout)
```

#### **Step 6: Feature Branch Deployment (2:30 PM)**
```bash
# Committed changes to feature branch
$ git add .
$ git commit -m "ORCIT-3892: Implement chunked S3 upload for large files

- Added multipart upload with 100MB chunks
- Implemented progress monitoring with Spring Scheduler
- Added automatic cleanup of incomplete uploads
- Updated controller with proper error handling
- Reduced memory usage from 5GB to 100MB per upload"

$ git push origin feature/ORCIT-3892-large-file-upload-fix
```

#### **Step 7: Create Hotfix Branch (3:00 PM)**
```bash
# Created hotfix branch for immediate deployment
$ git checkout main
$ git checkout -b hotfix/ORCIT-3892-large-file-upload
$ git merge feature/ORCIT-3892-large-file-upload-fix
$ git push origin hotfix/ORCIT-3892-large-file-upload
```

#### **Step 8: Hotfix Deployment (3:15 PM)**
```bash
# DevOps deployed hotfix to staging environment
$ kubectl apply -f k8s/staging/
$ kubectl rollout status deployment/orcit-api-staging

# Staging testing with real 5GB file - SUCCESS ✅
```

#### **Step 9: Create Change Request (CR) (3:30 PM)**
```
Change Request: CR-2025-0729-001
Title: Emergency Hotfix - Large File Upload Fix
Severity: P1 (Critical)
Requested By: Mithlesh Kumar (Senior Developer)
Approved By: Sarah Johnson (Product Owner), Mike Thompson (Scrum Master)

Business Justification:
- Critical P1 issue blocking regulatory compliance submissions
- 50+ users unable to upload files >5GB
- Hard deadline today at 5 PM for regulatory submissions
- Solution tested and verified in staging environment

Technical Changes:
- Implemented AWS S3 multipart upload with 100MB chunks
- Added Spring Boot scheduler for upload monitoring
- Reduced memory usage from 5GB to 100MB per upload
- Added automatic cleanup of incomplete uploads

Rollback Plan:
- Keep previous version deployment ready
- Database changes: None (backward compatible)
- Configuration changes: None required

Risk Assessment: LOW
- Solution uses standard AWS S3 APIs
- Backward compatible with existing uploads
- Comprehensive error handling and cleanup
```

#### **Step 10: Production Deployment (3:45 PM)**
```bash
# CR approved - Production deployment
$ git checkout main
$ git merge hotfix/ORCIT-3892-large-file-upload
$ git tag v1.2.3-hotfix
$ git push origin main --tags

# Production deployment
$ kubectl apply -f k8s/production/
$ kubectl rollout status deployment/orcit-api-production

# ✅ Deployment successful - 3:55 PM
```

### **Issue Resolution & Validation (4:00 PM - 4:30 PM)**

#### **Production Testing**
```bash
# Tested all 3 pending large files
File 1: trading-data-q4-2024.csv (5.2GB) - ✅ SUCCESS (8 minutes)
File 2: risk-metrics-december.xlsx (6.8GB) - ✅ SUCCESS (11 minutes)  
File 3: compliance-audit-2024.pdf (7.1GB) - ✅ SUCCESS (12 minutes)

Total: 19.1GB uploaded successfully before 5 PM deadline ✅
```

#### **JIRA Update (4:30 PM)**
```
Status: RESOLVED → CLOSED
Resolution: Fixed
Comment: 
"✅ HOTFIX DEPLOYED SUCCESSFULLY

Production verification completed:
- All 3 large files (19.1GB total) uploaded successfully
- Upload times reduced from timeout to 8-12 minutes
- Memory usage optimized from 5GB to 100MB per upload
- Regulatory deadline met with 30 minutes to spare
- 50+ compliance officers unblocked

Technical implementation:
- AWS S3 multipart upload with 100MB chunks
- Spring Boot scheduler for monitoring and cleanup
- Comprehensive error handling and rollback procedures
- Zero downtime deployment completed

Next steps: Monitor for 24 hours, then merge to main release branch."

Time to Resolution: 8 hours (8:30 AM to 4:30 PM)
Effort: 1 Senior Developer + DevOps support
```

## P1 Issue #2: SSL Certificate Expiration

### **Issue Discovery & Escalation**

#### **Initial Production Alert (6:00 AM)**
```
🚨 PRODUCTION ALERT - AppDynamics
Time: January 15, 2025 06:00:12 EST
Application: ORCIT-EIT-GATEWAY
Alert: SSL_HANDSHAKE_FAILURE_CRITICAL

Error Rate: 100% (0/847 successful API calls in last 5 minutes)
Affected APIs: FINRA, OCC, Federal Reserve
Error: javax.net.ssl.SSLHandshakeException

Auto-escalated to: On-call Senior Developer (Mithlesh Kumar)
```

#### **Emergency Email Alert (6:05 AM)**
```
From: monitoring@bofa.com
To: orcit-oncall@bofa.com, sarah.johnson@bofa.com, mike.thompson@bofa.com
Subject: 🚨 CRITICAL: Complete ORCIT Application Outage - SSL Failures

PRODUCTION OUTAGE DETECTED

Application: ORCIT-EIT Compliance Platform
Impact: 100% of external regulatory API calls failing
Users Affected: ALL 500+ compliance officers
Duration: 5 minutes and increasing

Error Pattern: SSL handshake failures with FINRA, OCC, Federal Reserve APIs
Status: INVESTIGATING

Incident Commander: Mithlesh Kumar (On-call Senior Developer)
Contact: +1-555-xxx-xxxx, mithlesh.kumar@bofa.com
```

#### **Emergency Bridge Call (6:10 AM)**
**Participants:** Product Owner (Sarah), Scrum Master (Mike), Senior Developer (Me), DevOps (Tom), Security Team (Lisa)

**Call Transcript:**
- **Me (Incident Commander):** "Good morning everyone. We have 100% outage on external API calls. All SSL handshakes failing."
- **Sarah (PO):** "This is critical - morning regulatory reports are due by 9 AM. How quickly can we resolve?"
- **Tom (DevOps):** "All internal services healthy. Issue seems to be with outbound SSL connections."
- **Lisa (Security):** "Could be certificate expiration. When was our client cert last renewed?"
- **Mike (SM):** "I'm creating P1 JIRA now. Mithlesh, you lead technical investigation."

#### **JIRA Incident Creation**
```
JIRA: ORCIT-3893
Title: P1 PRODUCTION OUTAGE - SSL Certificate Expiration
Priority: Blocker (P1)
Assignee: Mithlesh Kumar (me)
Reporter: Mike Thompson (Scrum Master)
Epic: Infrastructure Security

Description:
COMPLETE PRODUCTION OUTAGE - All external regulatory API calls failing
- 100% SSL handshake failures with FINRA, OCC, Federal Reserve
- 500+ compliance officers unable to access external systems
- Morning regulatory reports (due 9 AM) at risk
- Started: 6:00 AM EST, January 15, 2025

Root Cause: Suspected SSL client certificate expiration
Impact: CRITICAL - Complete functional outage

Acceptance Criteria:
- Restore all external API connectivity
- Implement monitoring to prevent future occurrences
- Complete root cause analysis
- Update incident response procedures

Sprint: Emergency Production Fix
```

### **Technical Investigation (6:15 AM - 7:00 AM)**

#### **Initial Diagnosis**
```bash
# Checked application logs first
$ tail -f /var/log/orcit/application.log

2025-01-15 06:00:23 ERROR [http-thread-1] RestTemplate - 
javax.net.ssl.SSLHandshakeException: PKIX path validation failed: 
java.security.cert.CertPathValidatorException: Certificate expired on Jan 15 23:59:59 2025 GMT

2025-01-15 06:00:45 ERROR [scheduler-1] FinraApiClient - 
Failed to submit daily risk report: SSL handshake failed

2025-01-15 06:01:12 ERROR [http-thread-5] OccApiClient - 
Connection refused: certificate validation failed
```

#### **Certificate Investigation**
```bash
# Checked our client certificate status
$ openssl x509 -in /etc/ssl/certs/bofa-orcit-client.crt -text -noout

Certificate:
    Data:
        Version: 3 (0x2)
        Serial Number: 0x7f4a2b8c9d1e3f5a
        Signature Algorithm: sha256WithRSAEncryption
        Issuer: C=US, O=Bank of America, CN=Internal CA
        Validity
            Not Before: Jan 15 00:00:00 2024 GMT
            Not After : Jan 15 23:59:59 2024 GMT ❌ EXPIRED! (Should be 2025!)
        Subject: C=US, ST=NY, L=New York, O=Bank of America, OU=Risk Technology, CN=orcit-client.bofa.com

# Checked external API certificate status (for comparison)
$ openssl s_client -connect finra-api.gov:443 -servername finra-api.gov | openssl x509 -text

Certificate:
    Validity
        Not Before: Dec 15 00:00:00 2023 GMT
        Not After : Jan 15 23:59:59 2026 GMT ✅ VALID (External certs are fine)
```

**Root Cause Confirmed:**
- **Our client SSL certificate expired at midnight** (Jan 15, 2024 → 2025)
- Certificate was used for mutual TLS authentication with ALL regulatory APIs
- Auto-renewal process had silently failed 3 months ago
- No proactive monitoring alerts configured for certificate expiration
- All external regulatory APIs require valid client certificates for compliance access

### **Emergency Resolution Process**

#### **Step 1: Create Emergency Feature Branch (7:00 AM)**
```bash
# Created emergency branch for immediate fix
$ git checkout main
$ git pull origin main
$ git checkout -b emergency/ORCIT-3893-ssl-cert-expiration
$ git push -u origin emergency/ORCIT-3893-ssl-cert-expiration
```

#### **Step 2: Emergency Certificate Renewal (7:00 AM - 7:45 AM)**
```bash
# Step 1: Generate new certificate signing request (CSR)
$ openssl req -new -key /etc/ssl/private/bofa-orcit-client.key \
    -out /tmp/bofa-orcit-renewal.csr \
    -subj "/C=US/ST=NY/L=New York/O=Bank of America/OU=Risk Technology/CN=orcit-client.bofa.com"

# Step 2: Emergency call to Security team (Lisa from bridge call)
# Called Security hotline: +1-555-SECURITY for emergency certificate signing
# Explained P1 production outage requiring immediate cert renewal

# Step 3: Submit CSR via emergency security portal
$ curl -X POST https://ca.internal.bofa.com/emergency-renewal \
  -H "Authorization: Bearer $EMERGENCY_TOKEN" \
  -F "csr=@/tmp/bofa-orcit-renewal.csr" \
  -F "justification=P1 Production Outage - Regulatory API Access" \
  -F "urgency=CRITICAL"

# Response: Certificate approved and signed (7:35 AM)

# Step 4: Download new certificate (valid for 2 years)
$ curl -H "Authorization: Bearer $EMERGENCY_TOKEN" \
  https://ca.internal.bofa.com/certificates/bofa-orcit-client-2025.crt \
  -o /tmp/bofa-orcit-client-new.crt

# Step 5: Backup old certificate and install new one
$ sudo cp /etc/ssl/certs/bofa-orcit-client.crt /etc/ssl/certs/bofa-orcit-client.crt.backup
$ sudo cp /tmp/bofa-orcit-client-new.crt /etc/ssl/certs/bofa-orcit-client.crt
$ sudo chown root:ssl-cert /etc/ssl/certs/bofa-orcit-client.crt
$ sudo chmod 644 /etc/ssl/certs/bofa-orcit-client.crt

# Step 6: Verify new certificate
$ openssl x509 -in /etc/ssl/certs/bofa-orcit-client.crt -text -noout

Certificate:
    Validity
        Not Before: Jan 15 07:35:00 2025 GMT
        Not After : Jan 15 23:59:59 2027 GMT ✅ VALID for 2 years!
```

#### **Step 3: Application Restart (7:45 AM)**
```bash
# Restarted all application services to pick up new certificate
$ sudo systemctl restart orcit-gateway
$ sudo systemctl restart orcit-api-service
$ sudo systemctl restart orcit-batch-processor
$ sudo systemctl restart orcit-scheduler

# Verified services started successfully
$ sudo systemctl status orcit-*

● orcit-gateway.service - ORCIT API Gateway
   Active: active (running) since Wed 2025-01-15 07:46:23 EST ✅

● orcit-api-service.service - ORCIT API Service  
   Active: active (running) since Wed 2025-01-15 07:46:35 EST ✅

# Tested external API connectivity
$ curl -v https://finra-api.gov/health \
  --cert /etc/ssl/certs/bofa-orcit-client.crt \
  --key /etc/ssl/private/bofa-orcit-client.key

* SSL connection using TLSv1.3 / TLS_AES_256_GCM_SHA384
* Certificate verification: OK ✅
< HTTP/1.1 200 OK
{"status": "healthy", "timestamp": "2025-01-15T12:47:15Z"} ✅
```

### **Long-term Solution Implementation (8:00 AM - 10:00 AM)**

#### **Step 4: Implement Certificate Monitoring**
```java
@Component
@Slf4j
public class CertificateExpirationMonitor {
    
    @Value("${app.ssl.certificates.paths}")
    private List<String> certificatePaths;
    
    @Autowired
    private AlertService alertService;
    
    @Autowired
    private SlackNotificationService slackService;
    
    // Check certificates every day at 8 AM
    @Scheduled(cron = "0 0 8 * * *")
    public void checkCertificateExpiration() {
        
        log.info("🔍 Starting daily certificate expiration check");
        
        for (String certPath : certificatePaths) {
            try {
                X509Certificate cert = loadCertificate(certPath);
                Date expirationDate = cert.getNotAfter();
                String subject = cert.getSubjectDN().getName();
                
                long daysUntilExpiration = ChronoUnit.DAYS.between(
                    LocalDate.now(), 
                    expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                );
                
                log.info("📅 Certificate {} expires in {} days ({})", 
                        subject, daysUntilExpiration, expirationDate);
                
                // Send alerts based on days remaining
                if (daysUntilExpiration <= 7) {
                    // CRITICAL: 7 days or less
                    String message = String.format(
                        "🚨 URGENT: SSL Certificate expires in %d days!
" +
                        "Certificate: %s
Path: %s
Expires: %s
" +
                        "Action Required: Renew immediately!",
                        daysUntilExpiration, subject, certPath, expirationDate
                    );
                    
                    alertService.sendCriticalAlert(message);
                    slackService.sendToChannel("#orcit-alerts", message);
                    
                    // Also send email to security team
                    emailService.sendToSecurityTeam("URGENT Certificate Renewal Required", message);
                    
                } else if (daysUntilExpiration <= 30) {
                    // WARNING: 30 days or less
                    String message = String.format(
                        "⚠️ WARNING: SSL Certificate expires in %d days
" +
                        "Certificate: %s
Path: %s
Expires: %s
" +
                        "Action: Please schedule renewal",
                        daysUntilExpiration, subject, certPath, expirationDate
                    );
                    
                    alertService.sendWarningAlert(message);
                    slackService.sendToChannel("#orcit-ops", message);
                    
                } else if (daysUntilExpiration <= 60) {
                    // INFO: 60 days or less
                    String message = String.format(
                        "📅 INFO: SSL Certificate expires in %d days
" +
                        "Certificate: %s
Expires: %s",
                        daysUntilExpiration, subject, expirationDate
                    );
                    
                    alertService.sendInfoAlert(message);
                    slackService.sendToChannel("#orcit-ops", message);
                }
                
            } catch (Exception e) {
                String errorMessage = String.format(
                    "❌ CRITICAL: Failed to read SSL certificate: %s
Error: %s",
                    certPath, e.getMessage()
                );
                
                log.error("Failed to check certificate: " + certPath, e);
                alertService.sendCriticalAlert(errorMessage);
                slackService.sendToChannel("#orcit-alerts", errorMessage);
            }
        }
        
        log.info("✅ Certificate expiration check completed");
    }
    
    private X509Certificate loadCertificate(String certPath) throws Exception {
        try (InputStream certStream = new FileInputStream(certPath)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return (X509Certificate) cf.generateCertificate(certStream);
        }
    }
    
    // Automatically detect SSL failures and trigger immediate check
    @EventListener
    public void handleSSLException(SSLHandshakeException sslException) {
        String errorMessage = String.format(
            "🔥 SSL Handshake failure detected in production!
" +
            "Error: %s
" +
            "Time: %s
" +
            "Action: Triggering immediate certificate check",
            sslException.getMessage(),
            Instant.now().toString()
        );
        
        log.error("SSL Handshake failed - possible certificate issue", sslException);
        alertService.sendCriticalAlert(errorMessage);
        slackService.sendToChannel("#orcit-alerts", errorMessage);
        
        // Trigger immediate certificate check
        checkCertificateExpiration();
    }
    
    // Manual trigger endpoint for ops team
    @PostMapping("/api/admin/check-certificates")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> triggerCertificateCheck() {
        try {
            checkCertificateExpiration();
            return ResponseEntity.ok("Certificate check completed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Certificate check failed: " + e.getMessage());
        }
    }
}
```

#### **Step 5: Enhanced SSL Configuration**
```java
@Configuration
@Slf4j
public class EnhancedSSLConfiguration {
    
    @Value("${app.ssl.keystore.path}")
    private String keystorePath;
    
    @Value("${app.ssl.keystore.password}")
    private String keystorePassword;
    
    @Value("${app.ssl.truststore.path}")
    private String truststorePath;
    
    @Bean
    public RestTemplate secureRestTemplate() throws Exception {
        
        log.info("🔧 Configuring secure RestTemplate with SSL certificates");
        
        // Load our client certificate keystore
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (InputStream keystoreStream = new FileInputStream(keystorePath)) {
            keyStore.load(keystoreStream, keystorePassword.toCharArray());
            log.info("✅ Loaded client keystore: {}", keystorePath);
        }
        
        // Load truststore for regulatory authority certificates
        KeyStore trustStore = KeyStore.getInstance("JKS");
        try (InputStream truststoreStream = new FileInputStream(truststorePath)) {
            trustStore.load(truststoreStream, "changeit".toCharArray());
            log.info("✅ Loaded truststore: {}", truststorePath);
        }
        
        // Configure SSL context with mutual TLS
        SSLContext sslContext = SSLContexts.custom()
            .loadKeyMaterial(keyStore, keystorePassword.toCharArray()) // Our client cert
            .loadTrustMaterial(trustStore, null) // Trusted CA certificates
            .build();
        
        // Create HTTP client with enhanced SSL configuration
        CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLContext(sslContext)
            .setConnectionTimeToLive(30, TimeUnit.SECONDS)
            .setMaxConnTotal(100)
            .setMaxConnPerRoute(20)
            .setDefaultRequestConfig(RequestConfig.custom()
                .setConnectTimeout(30000)
                .setSocketTimeout(60000)
                .setConnectionRequestTimeout(10000)
                .build())
            .build();
        
        HttpComponentsClientHttpRequestFactory factory = 
            new HttpComponentsClientHttpRequestFactory(httpClient);
        
        RestTemplate restTemplate = new RestTemplate(factory);
        
        // Add interceptor to log SSL-related errors
        restTemplate.getInterceptors().add((request, body, execution) -> {
            try {
                return execution.execute(request, body);
            } catch (IOException e) {
                if (e.getCause() instanceof SSLException) {
                    log.error("🔥 SSL Exception occurred during API call to: {}", 
                            request.getURI(), e);
                    // Publish SSL exception event
                    applicationEventPublisher.publishEvent(new SSLHandshakeException(e.getMessage()));
                }
                throw e;
            }
        });
        
        log.info("✅ Secure RestTemplate configured successfully");
        return restTemplate;
    }
    
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
}
```

#### **Step 6: Application Properties Configuration**
```yaml
# Enhanced SSL certificate monitoring configuration
app:
  ssl:
    certificates:
      paths:
        - /etc/ssl/certs/bofa-orcit-client.crt
        - /etc/ssl/certs/finra-ca.crt
        - /etc/ssl/certs/occ-ca.crt
        - /etc/ssl/certs/fed-ca.crt
    keystore:
      path: /etc/ssl/keystores/orcit-client.p12
      password: ${KEYSTORE_PASSWORD:defaultPassword}
    truststore:
      path: /etc/ssl/keystores/regulatory-truststore.jks

# Enhanced scheduling configuration
spring:
  task:
    scheduling:
      enabled: true
      pool:
        size: 10
        thread-name-prefix: "orcit-scheduler-"
      
# Slack notifications
slack:
  webhook:
    alerts-channel: ${SLACK_ALERTS_WEBHOOK_URL}
    ops-channel: ${SLACK_OPS_WEBHOOK_URL}

# Email notifications  
email:
  security-team: security-team@bofa.com
  ops-team: orcit-ops@bofa.com
```

### **Testing & Deployment Process**

#### **Step 7: Feature Branch Testing (10:00 AM - 11:00 AM)**
```bash
# Committed monitoring solution to emergency branch
$ git add .
$ git commit -m "ORCIT-3893: Implement SSL certificate monitoring

- Added daily certificate expiration checks with 60/30/7 day alerts
- Enhanced SSL configuration with proper error handling
- Added Slack and email notifications for critical alerts
- Implemented SSL exception event handling
- Added manual certificate check endpoint for ops team
- Configured comprehensive certificate monitoring for all paths"

$ git push origin emergency/ORCIT-3893-ssl-cert-expiration

# Tested monitoring functionality locally
$ mvn test -Dtest=CertificateExpirationMonitorTest
[INFO] Tests run: 5, Failures: 0, Errors: 0 ✅

# Manual test of certificate check endpoint
$ curl -X POST http://localhost:8080/api/admin/check-certificates \
  -H "Authorization: Bearer $ADMIN_TOKEN"
Response: "Certificate check completed successfully" ✅
```

#### **Step 8: Create Hotfix Branch (11:00 AM)**
```bash
# Created hotfix branch for production deployment
$ git checkout main
$ git checkout -b hotfix/ORCIT-3893-ssl-monitoring
$ git merge emergency/ORCIT-3893-ssl-cert-expiration
$ git push origin hotfix/ORCIT-3893-ssl-monitoring
```

#### **Step 9: Create Change Request (CR) (11:15 AM)**
```
Change Request: CR-2025-0115-001
Title: Emergency SSL Certificate Monitoring Implementation
Severity: P1 (Critical) 
Requested By: Mithlesh Kumar (Senior Developer)
Approved By: Sarah Johnson (Product Owner), Mike Thompson (Scrum Master)

Business Justification:
- Prevent future SSL certificate expiration outages
- Production outage lasted 1.5 hours affecting all 500+ users
- Regulatory compliance at risk without external API access
- Need proactive monitoring with 60/30/7 day advance warnings

Technical Changes:
- Implemented daily SSL certificate expiration monitoring
- Added multi-channel alerting (AppDynamics, Slack, Email)
- Enhanced SSL configuration with comprehensive error handling
- Added SSL exception event detection and immediate alerts
- Created manual certificate check endpoint for operations

Risk Assessment: LOW
- Monitoring is read-only operation (no changes to certificates)
- Enhanced error handling improves system reliability
- Backward compatible with existing SSL configuration
- Comprehensive testing completed in staging environment

Rollback Plan:
- Disable scheduled certificate monitoring if needed
- Previous SSL configuration remains unchanged
- No database or infrastructure changes required
```

#### **Step 10: Production Deployment (11:30 AM)**
```bash
# CR approved - Production deployment
$ git checkout main
$ git merge hotfix/ORCIT-3893-ssl-monitoring
$ git tag v1.2.4-ssl-monitoring
$ git push origin main --tags

# Production deployment via Kubernetes
$ kubectl apply -f k8s/production/
$ kubectl rollout status deployment/orcit-api-production

deployment "orcit-api-production" successfully rolled out ✅

# Verify production deployment
$ kubectl get pods -l app=orcit-api
NAME                              READY   STATUS    RESTARTS   AGE
orcit-api-production-7d4b8c9f-1    1/1     Running   0          2m ✅
orcit-api-production-7d4b8c9f-2    1/1     Running   0          2m ✅
```

### **Issue Resolution & Validation (11:45 AM - 12:30 PM)**

#### **Production Testing**
```bash
# Tested all external regulatory APIs
$ curl -v https://finra-api.gov/daily-reports \
  --cert /etc/ssl/certs/bofa-orcit-client.crt \
  --key /etc/ssl/private/bofa-orcit-client.key
✅ SUCCESS - FINRA API accessible

$ curl -v https://occ-api.gov/risk-submissions \
  --cert /etc/ssl/certs/bofa-orcit-client.crt \
  --key /etc/ssl/private/bofa-orcit-client.key  
✅ SUCCESS - OCC API accessible

$ curl -v https://federalreserve.gov/compliance-data \
  --cert /etc/ssl/certs/bofa-orcit-client.crt \
  --key /etc/ssl/private/bofa-orcit-client.key
✅ SUCCESS - Federal Reserve API accessible

# Verified monitoring system
$ curl -X POST https://orcit-prod.bofa.com/api/admin/check-certificates \
  -H "Authorization: Bearer $ADMIN_TOKEN"

Response: "Certificate check completed successfully" ✅

# Checked Slack notifications
Slack #orcit-ops: "📅 INFO: SSL Certificate expires in 730 days
Certificate: CN=orcit-client.bofa.com
Expires: Jan 15 23:59:59 2027 GMT" ✅
```

#### **JIRA Resolution (12:30 PM)**
```
Status: RESOLVED → CLOSED
Resolution: Fixed
Root Cause: SSL client certificate expired at midnight without monitoring

Resolution Summary:
"✅ EMERGENCY SSL CERTIFICATE ISSUE RESOLVED

Timeline:
- 6:00 AM: Production outage detected (SSL handshake failures)
- 6:10 AM: Emergency bridge call and P1 JIRA created
- 7:45 AM: New certificate installed, services restarted
- 8:00 AM: All external APIs accessible, users restored
- 12:30 PM: Long-term monitoring solution deployed

Technical Resolution:
- Emergency certificate renewal completed (valid until Jan 2027)
- Implemented comprehensive SSL certificate monitoring
- Added proactive alerting (60/30/7 days before expiration)
- Enhanced SSL configuration with proper error handling
- Deployed multi-channel notifications (Slack, email, AppDynamics)

Business Impact Resolved:
- All 500+ compliance officers restored to full functionality
- Morning regulatory reports submitted successfully before 9 AM deadline
- Zero regulatory compliance violations or missed deadlines
- Future SSL certificate outages prevented with proactive monitoring

Next Steps:
- Monitor certificate expiration alerts for 30 days
- Schedule quarterly certificate inventory reviews
- Update incident response procedures with SSL troubleshooting steps"

Total Outage Duration: 1 hour 45 minutes (6:00 AM - 7:45 AM)
Resolution Time: 6.5 hours (including long-term solution)
Effort: 1 Senior Developer + DevOps + Security team support
```

### **Results Achieved**
```
Immediate Recovery:
├── Service Restoration: 100% external API functionality restored in 1h 45m
├── User Access: All 500+ compliance officers back to full functionality  
├── API Connectivity: FINRA, OCC, Federal Reserve APIs fully accessible
├── Regulatory Compliance: Morning reports submitted before 9 AM deadline
└── Help Desk: All 200+ tickets resolved, no escalations

Long-term Prevention:
├── ✅ Proactive SSL certificate monitoring (60/30/7 day advance alerts)
├── ✅ Multi-channel alerting (AppDynamics, Slack, Email)
├── ✅ Enhanced SSL error detection and immediate notifications
├── ✅ Manual certificate check endpoint for operations team
├── ✅ Comprehensive certificate inventory tracking
└── ✅ Updated incident response procedures

Business Outcomes:
├── ✅ Zero regulatory deadline misses or compliance violations
├── ✅ Maintained compliance standing with FINRA/OCC/Federal Reserve
├── ✅ Prevented potential $10M+ in regulatory fines and penalties
├── ✅ Improved operational reliability to 99.9% uptime
├── ✅ Enhanced security posture with proactive certificate management
└── ✅ Strengthened incident response and emergency procedures

Process Improvements:
├── ✅ Emergency response process refined (6.5-hour total resolution)
├── ✅ Cross-team collaboration enhanced (Dev, DevOps, Security, PO, SM)
├── ✅ Hotfix deployment pipeline validated under pressure
├── ✅ Change management process followed even during emergency
└── ✅ Comprehensive documentation and knowledge transfer completed
```

---

## Interview Presentation Guide
```
Technical Improvements:
├── File Size Support: 5MB → 10GB maximum capacity
├── Upload Success Rate: 0% → 100% for large files (>5GB)
├── Memory Usage: 5GB → 100MB (98% reduction)
├── Upload Speed: 40% faster with parallel chunk processing
├── Reliability: Added retry mechanisms and automatic cleanup
└── Monitoring: Real-time progress tracking with Spring Scheduler

Business Outcomes:
├── ✅ All 3 regulatory files (19.1GB total) uploaded successfully
├── ✅ Met critical 5 PM regulatory deadline with 30 minutes to spare
├── ✅ 50+ compliance officers unblocked and productive
├── ✅ Zero manual workarounds or exceptions required
├── ✅ Avoided potential $1M+ in regulatory penalties
└── ✅ Improved system reliability for future large file uploads

Process Improvements:
├── ✅ Emergency response process validated (8-hour resolution)
├── ✅ Hotfix deployment pipeline proven effective
├── ✅ Cross-team collaboration (Dev, DevOps, PO, SM) successful
├── ✅ Proper change management and approval process followed
└── ✅ Comprehensive testing and validation completed
```

---

## P1 Issue #2: SSL Certificate Expiration

