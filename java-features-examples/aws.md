############################################################
## 1. Cloud Basics
############################################################

**What is AWS?**
Amazon Web Services (AWS) is a leading cloud platform offering on-demand infrastructure, storage, databases, networking, analytics, and more.

**Why use cloud?**
- Scalability, reliability, cost savings, global reach, rapid provisioning.

**Difference between on-premise and cloud deployment:**
| On-Premise                | Cloud (AWS)                  |
|---------------------------|------------------------------|
| Physical servers onsite    | Virtual resources online     |
| High upfront cost          | Pay-as-you-go pricing        |
| Manual scaling             | Auto scaling                 |
| Maintenance by user        | Managed by provider          |
| Limited flexibility        | Global accessibility         |

############################################################
## 2. AWS Services for Java Apps
############################################################

**EC2 (Elastic Compute Cloud):**
- Virtual machines for running Java apps.
- SSH access, custom AMIs, auto scaling, security groups.

**S3 (Simple Storage Service):**
- Object storage for files, backups, logs, and static assets.
- Java apps use AWS SDK for file upload/download.

**RDS (Relational Database Service):**
- Managed databases (PostgreSQL, MySQL, Oracle).
- Automated backups, scaling, high availability.

**Lambda:**
- Serverless functions for event-driven processing.
- Run Java code without managing servers.

**Elastic Beanstalk:**
- Easy deployment for Java web apps.
- Handles provisioning, load balancing, scaling, and monitoring.

**VPC (Virtual Private Cloud):**
- Isolated network environment for resources.
- Control subnets, routing, security groups.

**IAM (Identity & Access Management):**
- Manage users, roles, and permissions for AWS resources.
- Secure access control for apps and services.

############################################################
## 3. Deployment & CI/CD
############################################################

**Deploying Java Microservices to AWS:**
- Use EC2, Elastic Beanstalk, or Docker containers with ECS/EKS.
- CI/CD pipelines with Jenkins, GitHub Actions, or AWS CodePipeline automate build, test, and deploy steps.

############################################################
## 4. Integration
############################################################

**Connecting Java Apps to AWS Services:**
- Use AWS SDK for Java to interact with S3, RDS, DynamoDB, SQS, SNS.
- Configure credentials and endpoints in application properties.

############################################################
## 5. Security
############################################################

**Securing AWS Resources:**
- Use IAM roles and policies for fine-grained access control.
- Store secrets securely (AWS Secrets Manager, Parameter Store).

############################################################
## 6. Monitoring & Troubleshooting
############################################################

**CloudWatch:**
- Collect logs and metrics from EC2, Lambda, RDS, etc.
- Set up alerts and dashboards for health monitoring.

############################################################
## 7. Best Practices
############################################################

**Cost Optimization:**
- Use auto-scaling, reserved instances, and monitor usage.

**High Availability & Disaster Recovery:**
- Deploy across multiple availability zones.
- Use managed backups and failover strategies.

############################################################
## Sample AWS Interview Questions & Answers
############################################################

**Q: How would you deploy a Spring Boot app on AWS?**
A: Package the app as a JAR/WAR, deploy to EC2, Elastic Beanstalk, or Docker container on ECS/EKS. Use CI/CD for automation.

**Q: What is the difference between EC2 and Lambda?**
A: EC2 provides virtual machines for any workload; Lambda runs code serverlessly in response to events, no server management required.

**Q: How do you store and retrieve files from S3 in Java?**
A: Use AWS SDK for Java (`AmazonS3Client`) to upload/download files to S3 buckets.

**Q: How do you set up a CI/CD pipeline for AWS deployments?**
A: Use Jenkins, GitHub Actions, or AWS CodePipeline to automate build, test, and deploy steps to EC2, ECS, or Beanstalk.

**Q: What is IAM and how do you use it for access control?**
A: IAM manages users, roles, and permissions. Assign least-privilege roles to apps/services for secure access.

**Q: How do you monitor application health in AWS?**
A: Use CloudWatch for logs, metrics, alerts, and dashboards to monitor resource and app health.
