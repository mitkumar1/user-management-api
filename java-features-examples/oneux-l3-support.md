### Blue-Green Deployment Activities
  
  **How to Provision Two Identical Environments in Kubernetes (Blue/Green):**
  1. **Build Docker Image:**
     - Write a Dockerfile for your application.
     - Build and tag the image (e.g., `myapp:latest`).
  - Push the image to a container registry (Docker Hub, RMS Container Registry, etc.).
  2. **Create Kubernetes Deployments:**
     - Create two deployments in Kubernetes: one for Blue (e.g., `myapp-blue`) and one for Green (e.g., `myapp-green`).
     - Use separate namespaces or labels to distinguish environments.
     - Apply identical configuration, secrets, and resource limits to both deployments.
  3. **Configure NGINX/Ingress:**
     - Set up NGINX or Kubernetes Ingress to route traffic to the Blue deployment initially.
     - Define Ingress rules for switching traffic between Blue and Green.
  4. **Validate Deployments:**
     - Run health checks and smoke tests on both environments.
     - Ensure both are ready for production traffic.

  **Jenkins Pipeline Configuration for Blue-Green Deployment (Step-by-Step):**
  1. **Source Code Checkout:**
     - Jenkins pulls the latest code from Git.
  2. **Build & Test:**
     - Build the application and run unit/integration tests.
  3. **Docker Build & Push:**
     - Build Docker image using the Dockerfile.
     - Push the image to the container registry.
  4. **Deploy to Green Environment:**
     - Use `kubectl` or Helm to deploy the new image to the Green deployment (`myapp-green`).
  5. **Health Check:**
     - Run automated health checks and smoke tests on Green.
     - If successful, proceed; if not, abort and notify.
  6. **Switch Traffic:**
     - Update NGINX/Ingress rules to route traffic from Blue to Green.
  7. **Monitor:**
     - Monitor application health and logs in Green.
  8. **Rollback (if needed):**
     - If issues are detected, revert Ingress rules to route traffic back to Blue.
  9. **Clean Up:**
     - Once Green is stable, update Blue for the next cycle.

  **Sample Jenkins Pipeline (Declarative):**
  ```groovy
  pipeline {
    agent any
    stages {
      stage('Checkout') {
        steps { checkout scm }
      }
      stage('Build & Test') {
        steps { sh 'mvn clean install' }
      }
      stage('Docker Build & Push') {
        steps {
          sh 'docker build -t myapp:${BUILD_NUMBER} .'
          sh 'docker push myapp:${BUILD_NUMBER}'
        }
      }
      stage('Deploy to Green') {
        steps {
          sh 'kubectl set image deployment/myapp-green myapp=myapp:${BUILD_NUMBER}'
        }
      }
      stage('Health Check') {
        steps {
          // Add health check scripts or API calls here
        }
      }
      stage('Switch Traffic') {
        steps {
          // Update Ingress/NGINX config to point to myapp-green
        }
      }
      stage('Monitor') {
        steps {
          // Monitor logs, metrics, etc.
        }
      }
      stage('Rollback') {
        when { expression { return currentBuild.result == 'FAILURE' } }
        steps {
          // Revert Ingress/NGINX config to point to myapp-blue
        }
      }
    }
  }
  ```
  
  **What is Blue-Green Deployment?**
  Blue-green deployment is a release management strategy that reduces downtime and risk by running two identical production environments—“Blue” (current live) and “Green” (new version).
  
  **Why Use Blue-Green Deployment?**
  - Zero-downtime deployments: Users experience no interruption.
  - Easy rollback: Instantly revert to previous version if issues occur.
  - Safe testing: Validate new release in real environment before switching traffic.
  - Minimized risk: Problems in the new version don’t affect all users.
  
  **Step-by-Step Process:**
  1. Prepare Two Environments (Step-by-Step):
     - Provision two identical environments in Kubernetes: one for "Blue" (current live) and one for "Green" (new version).
     - Use separate namespaces or deployments for Blue and Green.
     - Build and deploy Docker images for both environments.
     - Configure NGINX as a load balancer or use Kubernetes Ingress to manage traffic routing.
     - Set up Ingress rules to direct user traffic to the Blue environment initially.
     - Ensure both environments have the same configuration, secrets, and access controls.
  2. Deploy New Version:
    - Deploy the new application (Green) using Docker containers and Kubernetes in the Green environment.
  3. Test Green Environment:
    - Run health checks, smoke tests, and validate all features in Green.
    - Only internal users or test traffic access Green.
  4. Switch Traffic:
    - Update load balancer or DNS to route all user traffic from Blue to Green.
    - Now, Green becomes the live environment.
  5. Monitor:
    - Closely monitor application health, logs, and user experience in Green.
  6. Rollback (if needed):
    - If issues are found, switch traffic back to Blue instantly.
  7. Clean Up:
    - Once Green is stable, Blue can be updated for the next deployment cycle.
  
  **Benefits:**
  - No downtime for users
  - Quick rollback in case of failure
  - Safe validation of new releases
  - Reduced deployment risk
  - Continuous delivery and faster releases
# OneUX L2/L3 Production Support Role & Responsibilities Guide

## Table of Contents
1. Overview
2. Core Responsibilities
3. Technical Skills & Tools
4. Incident & Problem Management Process
5. On-Call & Weekend Support
6. Key Accomplishments & Metrics
7. Interview Talking Points

---

## 1. Overview

**Role:** L2/L3 Production Support Engineer  
**Domain:** Enterprise Cloud Infrastructure  
**Primary Technologies:** Unix Shell Scripting, Oracle, Autosys, Hadoop  

---

### Step-by-Step: L3 Production Engineer Roles & Responsibilities

1. **Incident Detection & Response:**
  - Monitor production systems for alerts and anomalies using tools like Splunk, Dynatrace, and AppDynamics.
  - Respond immediately to critical (911/P1) incidents, join major incident bridges, and coordinate rapid service restoration.

2. **Triage & Diagnosis:**
  - Analyze incident tickets from Remedy, ServiceNow, or Jira.
  - Perform initial impact assessment and prioritize based on severity (P1–P4).
  - Use Unix shell scripts, database queries, and Autosys job logs to diagnose root causes.

3. **Resolution & Recovery:**
  - Develop and execute shell scripts for automated diagnostics, recovery, and health checks.
  - Troubleshoot and resolve issues in Oracle, Autosys, and Hadoop environments.
  - Restore services within defined SLAs for each incident type.

4. **Root Cause Analysis (RCA):**
  - Conduct deep-dive analysis for recurring or major incidents.
  - Document findings, corrective actions, and update SOPs/runbooks.

5. **Change & Release Management:**
  - Participate in CAB meetings, manage weekend releases, and verify post-deployment health.
  - Automate rollback and alert-response workflows using shell scripting.

6. **Collaboration & Communication:**
  - Work closely with application, infrastructure, and DBA teams to resolve cross-functional issues.
  - Communicate status and updates to business stakeholders during major incidents.
  - Mentor L1/L2 engineers, share knowledge, and build advanced triage capabilities.

7. **Continuous Improvement:**
  - Develop reusable shell script modules for automation and monitoring.
  - Improve incident/problem management processes and reduce repeat issues.
  - Maintain high system uptime and reliability through proactive monitoring and automation.

---

## 2. Core Responsibilities

- **Incident Management:**
  - Monitor production systems using Remedy, ServiceNow, and Jira
  - Triage, diagnose, and resolve P1–P4 incidents
  - Adhere to strict SLAs for incident response and resolution:
    - 911 (Critical Outage): Immediate response, restore service within 1 hour
    - P1 (High Priority): Response within 15 minutes, resolution within 4 hours
    - P2 (Major): Response within 30 minutes, resolution within 8 hours
    - P3 (Minor): Response within 2 hours, resolution within 24 hours
    - P4 (Low): Response within 4 hours, resolution within 72 hours
  - Lead major incident bridges and coordinate rapid service restoration
  - Document incident details, root cause, and resolution steps

- **Problem Management:**
  - Perform RCA (Root Cause Analysis) for recurring issues
  - Author corrective action plans and drive process improvements
  - Maintain knowledge base and SOPs for common problems

- **Production Support:**
  - Provide L2/L3 support for Unix, Oracle, Autosys, and Hadoop environments
  - Develop, maintain, and optimize Unix shell scripts for automation, monitoring, and recovery
  - Manage Autosys job scheduling, troubleshooting, and recovery
  - Support Oracle database operations, including health checks, log analysis, and query optimization
  - Monitor Hadoop clusters, troubleshoot data pipeline issues, and automate ETL tasks

- **Change & Release Management:**
  - Participate in CAB meetings, manage weekend releases, and perform post-deployment verification
  - Automate rollback and alert-response workflows using shell scripting
  - Perform deployment activities for RMS (Resource Management System) including:
    - Blue-green deployment strategy to minimize downtime and risk
    - Use Docker for containerization of application components
    - Deploy and orchestrate containers using Kubernetes
    - Validate health and functionality of new deployments before switching traffic
    - Rollback to previous version if post-deployment issues are detected

- **Collaboration & Communication:**
  - Work closely with application, infrastructure, and DBA teams
  - Communicate with business stakeholders during major incidents
  - Mentor L1/L2 engineers and build advanced triage capabilities

---

## 3. Technical Skills & Tools

- **Unix/Linux Shell Scripting:** Bash, Korn, Shell
  - **Bash (Bourne Again SHell):** The most widely used Unix shell, known for its scripting capabilities, command-line editing, and automation features. Used for writing scripts to automate tasks, manage files, and monitor systems.
  - **Korn Shell (ksh):** An enhanced Unix shell with advanced scripting features, better performance, and compatibility with older Bourne shell scripts. Preferred in some enterprise environments for its robust programming constructs.
  - **Shell (sh):** The original Unix shell, simple and lightweight, often used for basic scripting and compatibility. Many scripts start with `#!/bin/sh` for portability across Unix systems.
- **Database:** Oracle (SQL, PL/SQL, health checks, log analysis)
- **Job Scheduling:** Autosys (job creation, monitoring, troubleshooting)
- **Big Data:** Hadoop (HDFS, Hive, ETL automation)
- **Monitoring:** Splunk, Dynatrace, AppDynamics, Grafana
- **Incident Management:** Remedy, ServiceNow, Jira
- **Version Control:** Git, Bitbucket
- **Automation:** Python, Perl (as needed)
**Monitoring:** Splunk (Fast, Verbose, Smart search modes: Fast for speed, Verbose for full event details, Smart for balanced results), Dynatrace, AppDynamics, Grafana

---

## 4. Incident & Problem Management Process

### Incident Management
- **Detection:** Automated alerts via monitoring tools (Splunk, Dynatrace, AppDynamics)
- **Logging:** Incident tickets created in Remedy, ServiceNow, or Jira
- **Triage:** Initial assessment, impact analysis, and assignment
- **Resolution:** Troubleshoot using shell scripts, database queries, Autosys job logs, Hadoop diagnostics
- **Communication:** Update stakeholders, document progress, escalate as needed
- **Closure:** Document root cause, resolution, and preventive actions

### Problem Management
- **RCA:** Analyze recurring incidents, perform deep-dive diagnostics
- **Corrective Actions:** Implement fixes, update SOPs, automate detection
- **Knowledge Sharing:** Maintain runbooks, share lessons learned

---

## 5. On-Call & Weekend Support

- Participate in 24x7 rotational on-call schedule
- Provide escalation support during weekends and holidays
- Lead triage calls for critical incidents
- Ensure rapid diagnosis and resolution to minimize downtime
- Document all on-call activities and lessons learned

---

## 6. Key Accomplishments & Metrics

- Reduced incident resolution time by automating diagnostics with shell scripts
- Achieved 99.9% uptime for critical production systems
- Led remediation of major outages and improved system reliability
- Developed reusable shell script modules for L3 support teams
- Improved RCA quality and reduced repeat incidents
- Supported successful weekend releases and post-deployment verifications

---

## 7. Interview Talking Points

- End-to-end incident management using Remedy, ServiceNow, Jira
- Advanced Unix shell scripting for automation and recovery
  - Advanced Unix shell scripting involves writing and maintaining shell scripts to automate repetitive tasks, monitor system health, and recover from failures efficiently.
  - **What is Unix Shell Scripting?**
    - Shell scripting is the process of writing a series of commands in a file (script) to be executed by the Unix shell (e.g., Bash, Korn, Shell).
    - It enables automation of tasks such as file manipulation, process management, monitoring, and reporting.
  - **Basic Unix Commands Commonly Used:**
    - `ls` – List files and directories
    - `cd` – Change directory
    - `pwd` – Print working directory
    - `cp` – Copy files
    - `mv` – Move/rename files
    - `rm` – Remove files
    - `cat` – Display file contents
    - `grep` – Search for patterns in files
    - `tail` – View end of files (useful for logs)
    - `chmod` – Change file permissions
    - `ps` – Display running processes
    - `top` – Monitor system resource usage
    - `df` – Check disk space
    - `du` – Check directory/file size
    - `find` – Search for files
    - `echo` – Print text to terminal
    - `sh`/`bash` – Execute shell scripts
- Autosys job scheduling and troubleshooting
  - **What is Autosys?**
   - Autosys is an enterprise job scheduling and automation tool used to manage, monitor, and automate batch jobs across distributed systems.
   - It provides centralized control, advanced scheduling, dependency management, and alerting for critical business processes.
  - **Why Use Autosys?**
   - Ensures reliable and timely execution of batch jobs (ETL, data loads, file transfers, etc.).
   - Supports complex job dependencies, calendars, and event-based triggers.
   - Provides monitoring, alerting, and troubleshooting capabilities for production support teams.
  - **How to Use Autosys (Step-by-Step):**
   1. **Job Definition:**
     - Define jobs using JIL (Job Information Language) or Autosys GUI.
     - Specify job type (command, box, file watcher), schedule, dependencies, and notifications.
   2. **Job Scheduling:**
     - Assign jobs to run at specific times, dates, or based on events/calendars.
   3. **Job Monitoring:**
     - Use Autosys commands (`autorep`, `job_depends`, `autosyslog`) or GUI to monitor job status, history, and logs.
   4. **Troubleshooting:**
     - Investigate job failures using logs, exit codes, and dependencies.
     - Restart, hold, or terminate jobs as needed using Autosys commands (`sendevent`).
   5. **Alerting & Notification:**
     - Configure email or SNMP alerts for job failures, delays, or completions.
   6. **Reporting:**
     - Generate reports on job runs, failures, and SLA compliance for audit and analysis.
- Oracle database health checks and log analysis
  - **Oracle Database Health Checks and Log Analysis:**
    - Perform regular health checks using SQL queries, monitoring tools, and Oracle Enterprise Manager to ensure database uptime, performance, and integrity.
    - Analyze database logs for errors, slow queries, deadlocks, and connection issues.
    - Optimize queries, review execution plans, and recommend indexing or partitioning improvements.
    - Automate routine checks and reporting using shell scripts and SQL scripts.
    - Collaborate with the DB team for advanced troubleshooting, patching, backup/restore, and performance tuning.
    - Escalate critical issues to DBAs and participate in joint RCA sessions for major incidents.
    - Document findings, resolutions, and preventive actions in runbooks and incident tickets.
- Hadoop cluster monitoring and ETL automation
  - **Hadoop Cluster Monitoring and ETL Automation:**
   - **Why Use Hadoop?**
    - Hadoop enables scalable storage and processing of large datasets using distributed computing (HDFS, MapReduce, Hive, Spark).
    - ETL (Extract, Transform, Load) automation ensures reliable, repeatable data ingestion, transformation, and loading into data warehouses or analytics platforms.
    - Monitoring is essential to maintain cluster health, performance, and data integrity.
   - **Step-by-Step Process:**
    1. **Cluster Health Monitoring:**
      - Use tools like Ambari, Cloudera Manager, or custom scripts to monitor node status, disk usage, memory, CPU, and network.
      - Set up alerts for node failures, disk full, high resource usage, and service outages.
    2. **Job Monitoring:**
      - Track ETL job status using YARN ResourceManager, job history server, or workflow schedulers (Oozie, Airflow).
      - Review logs for failed jobs, long-running tasks, and bottlenecks.
    3. **ETL Automation:**
      - Develop and schedule ETL workflows using Hive, Spark, or Pig scripts.
      - Automate job triggers using Autosys, Oozie, or Airflow based on time, events, or data arrival.
      - Validate data quality and completeness after each ETL run.
    4. **Troubleshooting:**
      - Investigate job failures using logs, error codes, and resource metrics.
      - Restart failed jobs, reprocess data, and escalate persistent issues to Hadoop admins or data engineers.
    5. **Collaboration:**
      - Work with data engineering teams for schema changes, performance tuning, and cluster upgrades.
      - Document issues, resolutions, and preventive actions in runbooks and incident tickets.
- RCA and problem management best practices
  - **RCA and Problem Management Best Practices:**
    - **Why Practice RCA and Problem Management?**
      - Prevent recurring incidents and improve system reliability.
      - Identify root causes, not just symptoms, to drive permanent fixes.
      - Enhance knowledge sharing and process improvement across teams.
    - **Day-to-Day Activities:**
      1. Review incident tickets and identify patterns or repeat issues.
      2. Conduct RCA sessions for major or recurring incidents, involving relevant stakeholders.
      3. Document findings, corrective actions, and preventive measures in runbooks and knowledge bases.
      4. Track implementation of corrective actions and verify effectiveness.
      5. Update SOPs and automation scripts based on lessons learned.
      6. Share RCA outcomes and best practices with cross-functional teams during regular meetings.
      7. Monitor for new incidents and proactively address emerging risks.
- On-call and weekend support experience
  - **On-Call and Weekend Support Experience:**
    - **How On-Call Support Works:**
      - Be available via cell phone and online (email, chat, incident management tools) during assigned on-call shifts.
      - Respond promptly to alerts, incident tickets, and escalation calls at any time (day or night).
      - Join triage bridges, coordinate with teams, and drive rapid resolution for critical issues.
      - Document actions, resolutions, and lessons learned for each incident handled during on-call.
    - **Why On-Call and Weekend Support is Needed:**
      - Ensures 24x7 coverage for production systems, minimizing downtime and business impact.
      - Supports urgent incident response outside regular business hours, including weekends and holidays.
      - Maintains high system availability and meets SLAs for critical applications.
      - Provides escalation support for L1/L2 teams and ensures expert intervention for major incidents.
- Collaboration with cross-functional teams
- Continuous improvement and process automation

---

*This document provides a comprehensive overview of L2/L3 production support responsibilities, focusing on Unix, Oracle, Autosys, and Hadoop environments, with emphasis on incident/problem management, automation, and operational excellence.*

---

## Sample P1 Issues Resolved Independently (Step-by-Step)

### 1. Application Login Failure Due to Expired SSL Certificate
**Issue:**
Users reported they were unable to log in to the application. The incident was classified as P1 due to business impact.
The issue was first reported by the customer support team after receiving multiple complaints from end users. Approximately 500+ users across different business units were impacted, unable to access critical application features required for daily operations. The incident was classified as P1 because it affected a large user base, disrupted business continuity, and required immediate resolution to restore service availability.
The end users included internal employees from sales, operations, and finance departments who rely on the application for daily business transactions, reporting, and customer management activities.

**Steps Taken:**
1. Received alert and incident ticket via ServiceNow.
2. Verified the issue by attempting login and checking error messages.
3. Checked application logs and found SSL handshake errors indicating certificate expiry.
4. Confirmed certificate status using `openssl` command and application configuration files.
5. Coordinated with the security team to obtain a renewed certificate.
6. Updated the certificate in the application server and restarted the service.
7. Validated successful login for multiple users and monitored logs for further errors.
8. Communicated resolution to stakeholders and closed the incident ticket.
9. Documented the issue and preventive steps in the runbook (e.g., set up certificate expiry alerts).

**How to explain this P1 issue in an interview:**
"I resolved a P1 incident where users were unable to log in due to an expired SSL certificate. My approach was systematic: I first verified the issue by reproducing the login failure and checking error messages. I then reviewed application logs and identified SSL handshake errors. Using the `openssl` command, I confirmed the certificate had expired. I coordinated with the security team to obtain a renewed certificate, updated it on the server, and restarted the service. After validating successful logins for multiple users, I communicated the resolution to stakeholders and documented preventive steps, such as setting up certificate expiry alerts. This ensured minimal downtime and improved future readiness."

### 2. Database Connection Timeout Due to Maxed-Out Connections
**Issue:**
Production outage occurred as the application could not connect to the Oracle database. Classified as P1 due to service unavailability.

**Steps Taken:**
1. Received automated alert and incident ticket from monitoring tools.
2. Checked application logs and found database connection timeout errors.
  - Accessed the application server via SSH.
  - Navigated to the application log directory (e.g., `/var/log/app/` or `/opt/app/logs/`).
  - Used commands like `tail -n 100 app.log` to view recent log entries and `grep 'timeout' app.log` to search for timeout-related errors.
  - Identified repeated error messages such as `ORA-12170: TNS:Connect timeout occurred` and `Database connection timeout` indicating connectivity issues.
   - Alternatively, you can check for the same errors in monitoring tools like Dynatrace and Splunk:
     - **Dynatrace:**
       1. Log in to the Dynatrace dashboard.
       2. Navigate to the relevant application or service.
       3. Use the "Logs" or "Problems" section to search for keywords like "timeout", "ORA-12170", or "database connection".
       4. Review the timeline and error details to correlate with the incident time.
     - **Splunk:**
       1. Log in to the Splunk web interface.
       2. Select the appropriate index or source type for application logs.
           - Typically, the index for application logs is already created and managed by the Splunk admin team. As a support engineer, you select the existing index (e.g., `main`, `app_logs`, or a project-specific index) for your searches. If a new log source needs to be onboarded, coordinate with the Splunk admin team to create and configure the required index.
       3. Use the search bar to run queries such as `timeout OR ORA-12170 OR "database connection"`.
       4. Filter results by time range and review log events for connection timeout errors.
3. Logged into the Oracle database and ran queries to check active sessions (`SELECT * FROM v$session WHERE status='ACTIVE';`).
  - Connected to the Oracle database using Oracle SQL Developer.
  - Authenticated with appropriate credentials (username and password) having necessary privileges.
  - Executed the SQL query `SELECT * FROM v$session WHERE status='ACTIVE';` to list all currently active sessions.
  - Reviewed the output to identify the number of active sessions, session details (user, machine, program), and any sessions that might be holding connections unnecessarily.
4. Identified a large number of inactive sessions holding connections.
5. Killed inactive sessions using SQL commands to free up connections.
6. Increased the connection pool size in the application configuration as a preventive measure.
7. Restarted affected application services to re-establish database connectivity.
8. Verified application availability and monitored for further issues.
9. Updated the runbook with troubleshooting steps and recommended regular session monitoring.

**How to explain this P1 issue in an interview:**
"I handled a P1 outage where the application could not connect to the Oracle database due to maxed-out connections. I started by checking application logs and identified connection timeout errors. I used Dynatrace and Splunk to correlate and confirm the issue. Next, I logged into the Oracle database using SQL Developer and ran queries to check active sessions, finding a large number of inactive sessions holding connections. I killed the inactive sessions to free up resources and increased the connection pool size in the application configuration. After restarting the affected services, I verified restored connectivity and documented the troubleshooting steps for future reference. This approach demonstrated my ability to quickly diagnose, resolve, and prevent critical production issues."

#######################################
## Splunk: Dashboards, Alerts, and Daily Activities

### Step-by-Step: Creating Dashboards in Splunk
1. Log in to the Splunk web interface with your credentials.
2. Navigate to the "Search & Reporting" app.
3. Use the search bar to run queries for your log data (e.g., `error OR timeout OR failed`).
4. Refine your search using time range, index, and source type filters.
5. Once you have a useful search, click "Save As" > "Dashboard Panel".
6. Create a new dashboard or add to an existing one. Name the panel and configure its visualization (table, chart, etc.).
7. Customize dashboard layout, add multiple panels for different metrics (errors, performance, user activity).
8. Share the dashboard with your team or set permissions as needed.

### Step-by-Step: Creating Alerts in Splunk
1. Run a search that identifies the condition you want to monitor (e.g., `error OR timeout`).
2. Click "Save As" > "Alert".
3. Set alert type (scheduled or real-time), trigger conditions (number of results, specific pattern), and time window.
4. Configure alert actions: email notification, webhook, or run a script.
5. Test the alert to ensure it triggers as expected.
6. Document the alert logic and recipients in your runbook.

### Day-to-Day Activities in Splunk (Support Engineer)
- Monitor dashboards for spikes in errors, performance issues, or unusual activity.
- Investigate alerts and correlate with incidents or tickets.
- Run ad-hoc searches to troubleshoot new issues or verify fixes.
- Review log trends for recurring problems and escalate to development or infrastructure teams as needed.
- Collaborate with Splunk admins to onboard new log sources or optimize existing dashboards.
- Document findings, update runbooks, and share knowledge with the team.
- Participate in regular dashboard reviews to ensure monitoring coverage aligns with business needs.

### Common Splunk Interview Questions (Senior Support Engineer)
1. How do you create and share a dashboard in Splunk? What best practices do you follow?
2. Explain the difference between real-time and scheduled alerts. When would you use each?

**Step-by-Step Explanation:**

**Real-Time Alerts:**
1. Real-time alerts continuously monitor incoming data and trigger as soon as the specified condition is met.
2. Use for critical issues that require immediate attention (e.g., security breaches, system outages).
3. In Splunk, create a search for your condition, click "Save As" > "Alert", and select "Real-time" as the alert type.
4. Set trigger conditions (e.g., number of results, specific error pattern).
5. Configure alert actions (email, webhook, script) for instant notification.
6. Test the alert to ensure it triggers immediately when the condition occurs.

**Scheduled Alerts:**
1. Scheduled alerts run searches at defined intervals (e.g., every 5 minutes, hourly, daily).
2. Use for periodic checks, trend monitoring, or non-urgent issues (e.g., daily error summary, weekly performance report).
3. In Splunk, create a search for your condition, click "Save As" > "Alert", and select "Scheduled" as the alert type.
4. Set the schedule (frequency and time window) for the search to run.
5. Define trigger conditions (e.g., number of results, threshold exceeded).
6. Configure alert actions (email, webhook, script) for notification.
7. Test the alert to ensure it triggers as per the schedule.

**Summary:**
- Real-time alerts = immediate response for urgent issues.
- Scheduled alerts = regular checks for ongoing monitoring and reporting.
3. How do you optimize Splunk searches for performance?
4. Describe a time you used Splunk to resolve a critical production issue. What was your approach?
5. How do you onboard a new log source into Splunk? What steps are involved?
6. What are indexes in Splunk, and how do you select the right one for your search?
7. How do you correlate data from multiple sources in Splunk?
8. What steps do you take to ensure Splunk dashboards and alerts remain relevant as applications evolve?
9. How do you handle false positives in Splunk alerts?
10. Can you explain the role of Splunk admin vs. support engineer in managing Splunk infrastructure?

---
### Senior Support Engineer: Interview Questions & Answers

**Q1: How do you create and share a dashboard in Splunk? What best practices do you follow?**
A1: I create dashboards by saving useful searches as dashboard panels, organizing them by use case (errors, performance, user activity), and sharing them with relevant teams. Best practices include using clear naming conventions, limiting search time ranges for performance, and regularly reviewing dashboards for relevance.

**Q2: Explain the difference between real-time and scheduled alerts. When would you use each?**
A2: Real-time alerts trigger immediately when a condition is met, ideal for critical issues needing instant action. Scheduled alerts run at set intervals, suitable for periodic checks or trend monitoring. I use real-time for outages and scheduled for daily health checks.

**Q3: How do you optimize Splunk searches for performance?**
A3: I optimize searches by specifying indexes and time ranges, using fields instead of full-text search, leveraging summary indexing, and avoiding wildcard searches. I also use event sampling and review search job inspector for bottlenecks.

**Q4: Describe a time you used Splunk to resolve a critical production issue. What was your approach?**
A4: I once resolved a P1 outage by correlating error logs across multiple sources in Splunk, identifying a failed database connection. I created a dashboard to monitor connection errors and set up alerts for future incidents, ensuring rapid detection and resolution.

**Q5: How do you onboard a new log source into Splunk? What steps are involved?**
A5: I coordinate with Splunk admins to configure inputs, select or create the appropriate index, define source types, and validate data ingestion. I then update dashboards and alerts to include the new source and document the process in the runbook.

---
### Senior Architect Developer: Interview Questions & Answers

**Q1: How do you design scalable log aggregation and monitoring for enterprise applications?**
A1: I design scalable log aggregation using centralized platforms like Splunk or ELK, ensuring all services log in a consistent format. I use log shippers (e.g., Fluentd, Logstash), partition logs by environment, and implement retention policies. Dashboards and alerts are tailored for business and technical stakeholders.

**Q2: What strategies do you use to ensure high availability and fault tolerance in production support systems?**
A2: I use redundant infrastructure, load balancing, automated failover, and health checks. I design for statelessness where possible, automate recovery scripts, and leverage cloud-native features for scaling and resilience.

**Q3: How do you integrate monitoring tools (Splunk, Dynatrace) with CI/CD pipelines?**
A3: I integrate monitoring by adding automated health checks and log validation steps in CI/CD pipelines. Post-deployment, I trigger synthetic tests and monitor dashboards for anomalies, enabling rapid rollback if issues are detected.

**Q4: How do you handle log volume growth and retention in large-scale environments?**
A4: I implement log rotation, compression, and tiered storage. I set retention policies based on compliance and business needs, archive older logs, and use summary indexing for long-term analytics.

**Q5: What is your approach to designing dashboards and alerts for both technical and business users?**
A5: I design dashboards with clear KPIs, use visualizations suited to the audience, and provide drill-down capabilities for technical users. Alerts are prioritized by impact and routed to the right teams, with actionable details included.

---
### Scenario-Based Splunk Interview Questions & Answers (Super Power User)

**Scenario 1: Troubleshooting a Sudden Spike in Errors**
Q: You notice a sudden spike in error logs on a production dashboard. How would you investigate and resolve the issue using Splunk?
A: I would first narrow the time range and identify the error pattern using Splunk search. Then, I would correlate errors with recent deployments or configuration changes, drill down by host, application, or user, and use transaction or stats commands to isolate the source. I would set up a real-time alert for recurrence and document findings for the team.

**Scenario 2: Onboarding a New Application’s Logs**
Q: A new application is going live. What steps do you take to onboard its logs into Splunk and ensure proper monitoring?
A: I coordinate with the app team to understand log format and requirements, work with Splunk admins to configure inputs and indexes, validate data ingestion, and create dashboards/alerts for key metrics. I review with stakeholders and update runbooks for future reference.

**Scenario 3: Performance Optimization**
Q: A dashboard is slow to load and searches are timing out. How do you optimize Splunk performance?
A: I review search queries for inefficient patterns, limit time ranges, specify indexes, and use summary indexing or data models for heavy reports. I check resource usage and consult the Splunk job inspector for bottlenecks.

**Scenario 4: Security Incident Investigation**
Q: Security alerts indicate possible unauthorized access. How do you use Splunk to investigate?
A: I search for suspicious login attempts, access patterns, or privilege escalations, correlate with network and application logs, build a timeline of events, and export findings for the security team. I also set up alerts for future similar activity.

**Scenario 5: False Positives in Alerts**
Q: Your team is receiving too many false positive alerts. How do you address this?
A: I analyze alert conditions, refine search criteria, add thresholds, exclusions, or suppression rules, and test changes. I gather feedback from users and continuously improve alert logic to reduce noise.

#######################################
## Grafana & Prometheus: Usage, Interview Questions, and Scenarios

### How to Use Grafana (Step-by-Step)
1. Log in to the Grafana web interface.
2. Add a data source (e.g., Prometheus, MySQL, Elasticsearch) via the "Configuration" menu.
3. Create a new dashboard and add panels for metrics (CPU, memory, application errors, etc.).
4. Use Prometheus queries (PromQL) to fetch and visualize time-series data.
5. Customize panel types (graphs, tables, heatmaps) and set thresholds for alerts.
6. Share dashboards with your team or set permissions for access control.
7. Set up alerting rules to notify via email, Slack, or webhook when thresholds are breached.

### How to Use Prometheus (Step-by-Step)
1. Install and configure Prometheus server.
2. Define scrape targets in `prometheus.yml` (applications, exporters, services).
3. Use exporters (node_exporter, blackbox_exporter, etc.) to collect metrics from systems and applications.
4. Write PromQL queries to analyze and aggregate metrics.
5. Integrate Prometheus with Grafana for advanced visualization and alerting.
6. Set up recording rules and alerting rules in Prometheus for automated monitoring.

### Common Grafana & Prometheus Interview Questions
1. How do you connect Prometheus as a data source in Grafana?
2. What is PromQL and how do you use it to query metrics?
3. How do you create and share dashboards in Grafana?
4. How do you set up alerting in Grafana and Prometheus?
5. What are exporters in Prometheus and how do you use them?
6. How do you troubleshoot missing or delayed metrics in Grafana?
7. How do you optimize Prometheus performance for large-scale environments?
8. How do you manage dashboard versioning and sharing in Grafana?
9. What is the difference between recording rules and alerting rules in Prometheus?
10. How do you secure Grafana and Prometheus in a production environment?

**Answers:**

1. **How do you connect Prometheus as a data source in Grafana?**
  - In Grafana, go to "Configuration" > "Data Sources" > "Add data source". Select Prometheus, enter the Prometheus server URL, and click "Save & Test" to verify the connection.

2. **What is PromQL and how do you use it to query metrics?**
  - PromQL (Prometheus Query Language) is used to query and aggregate time-series data in Prometheus. You use PromQL in Grafana panels or Prometheus UI to filter, sum, average, and visualize metrics (e.g., `avg(rate(http_requests_total[5m])) by (instance)`).

3. **How do you create and share dashboards in Grafana?**
  - Click "+" > "Dashboard" in Grafana, add panels with queries and visualizations, and save the dashboard. Use the "Share" button to generate a link or export the dashboard JSON for sharing with others or importing into another Grafana instance.

4. **How do you set up alerting in Grafana and Prometheus?**
  - In Grafana, configure alert rules on panels, set conditions, and add notification channels (email, Slack, webhook). In Prometheus, define alerting rules in `alert.rules` and configure Alertmanager to handle notifications and routing.

5. **What are exporters in Prometheus and how do you use them?**
  - Exporters are agents that collect metrics from systems or applications and expose them in Prometheus format (e.g., node_exporter for OS metrics, blackbox_exporter for endpoint checks). You configure Prometheus to scrape these exporters by adding their endpoints to `prometheus.yml`.

6. **How do you troubleshoot missing or delayed metrics in Grafana?**
  - Check Prometheus scrape intervals and target status, verify exporter health, review PromQL queries and time ranges in Grafana, and inspect logs for errors. Ensure network connectivity and that data retention policies are not causing data loss.

7. **How do you optimize Prometheus performance for large-scale environments?**
  - Use sharding and federation, optimize scrape intervals, limit retention duration, use remote storage, and monitor resource usage. Reduce the number of high-cardinality metrics and use recording rules for expensive queries.

8. **How do you manage dashboard versioning and sharing in Grafana?**
  - Use dashboard JSON export/import for version control, leverage Grafana’s built-in revision history, and use folders and permissions for organization. For teams, use Git or a dashboard-as-code tool (e.g., Grafana Terraform provider).

9. **What is the difference between recording rules and alerting rules in Prometheus?**
  - Recording rules precompute and store query results for faster access and reuse. Alerting rules evaluate conditions and trigger alerts when thresholds are met. Both are defined in Prometheus configuration but serve different purposes.

10. **How do you secure Grafana and Prometheus in a production environment?**
  - Enable authentication (LDAP, OAuth, SSO) in Grafana, restrict user roles and dashboard access, use HTTPS for all endpoints, and limit network exposure. For Prometheus, restrict API access, use HTTPS, and secure Alertmanager. Regularly audit user activity and update credentials.

### Scenario-Based Interview Questions & Answers

**Scenario 1: Dashboard Not Showing Latest Metrics**
Q: Your Grafana dashboard is not displaying the latest metrics from Prometheus. How do you troubleshoot?
A: I check Prometheus targets and scrape intervals, verify exporter status, and ensure Prometheus is collecting data. I review PromQL queries in Grafana panels, check time range settings, and look for errors in Grafana and Prometheus logs. I also confirm network connectivity between Grafana and Prometheus.

**Scenario 2: Setting Up Alerts for High CPU Usage**
Q: How do you set up an alert in Grafana for high CPU usage detected by Prometheus?
A: I create a panel in Grafana using a PromQL query for CPU usage, set a threshold for alerting, and configure notification channels (email, Slack, etc.). I test the alert to ensure it triggers when CPU usage exceeds the defined limit.

**Scenario 3: Scaling Prometheus for Large Environments**
Q: Prometheus is struggling to keep up with a large number of targets. What steps do you take?
A: I shard Prometheus servers, use federation to aggregate metrics, optimize scrape intervals, and leverage remote storage solutions. I monitor resource usage and tune retention policies for efficient storage.

**Scenario 4: Securing Grafana Dashboards**
Q: How do you secure access to sensitive dashboards in Grafana?
A: I use Grafana’s built-in user roles and permissions, enable authentication (LDAP, OAuth), and restrict access to sensitive dashboards. I audit user activity and regularly review access controls.

################################################
# Introduction: How to Give a Strong, Senior-Level Interview Introduction

Hello, Good morning. My name is Mithlesh Kumar. I am a results-driven Senior Production Support Engineer and Full Stack Developer with a Master of Computer Application (MCA) from University of Pune and a Bachelor of Computer Application (BCA) from Magadh University.

With over 7 years at Wipro, I currently lead L3 Production Support for the Cisco client, where I specialize in rapid incident resolution, root cause analysis, and automation of support workflows. My expertise includes:
- Proactively managing and resolving complex production incidents, minimizing downtime and business impact
- Driving permanent fixes through deep-dive RCA and process improvement
- Implementing advanced monitoring and health checks for high availability
- Automating diagnostics, recovery, and deployment support using shell scripting and Python
- Coordinating cross-functional teams to resolve critical issues and optimize service reliability
- Ensuring compliance with SLAs, change management, and audit requirements
- Leading on-call and weekend support, mentoring junior engineers, and preparing detailed incident reports

Previously, I supported risk and compliance applications for First Horizon Bank and Wells Fargo Bank as an L3 Engineer, where I:
- Led resolution of high-priority incidents in mission-critical banking systems
- Performed regular health checks, preventive maintenance, and process optimization
- Collaborated with business and technical teams to drive rapid service restoration
- Authored knowledge base articles and improved SOPs for recurring issues

As a Java Full Stack Developer for AT&T, T-Mobile, and BT, I architected and delivered scalable microservices solutions using:
- Backend: Core Java (8, 11, 17), Spring Boot, JPA, Hibernate, Microservices, Spring Security
- Frontend: Angular 11, ReactJS
- DevOps: CI/CD (Jenkins, CloudBees), Git, Maven, Gradle, UCD, Docker, Kubernetes, Helm Charts, Rancher, GitOps
- Cloud: PCF, Azure
- Microservices: Service Discovery (Eureka), API Gateway (Spring Cloud Gateway), Load Balancing, Circuit Breakers (Resilience4j, Hystrix), Kafka messaging, Config Server, Distributed Tracing (Sleuth, Zipkin), Monitoring (Prometheus, Grafana, AppDynamics, Dynatrace), Security (OAuth2, JWT, SAML2, Okta, mTLS, CSRF), Database (Oracle, HikariCP, JPA/Hibernate)

I am certified in OCJP 6 & 8, Azure, and PCF, and I consistently apply Agile Scrum practices in fast-paced, enterprise environments. My blend of production support and development experience enables me to deliver robust, automated solutions, drive continuous improvement, and mentor teams to achieve operational excellence.

################################################