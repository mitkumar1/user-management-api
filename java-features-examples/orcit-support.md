# ORCIT-EIT L3 Production Support Responsibilities
## Bank of America – Risk & Compliance Team

### Role Overview

As an L3 Production Support Engineer for the ORCIT-EIT application in the BofA Risk & Compliance team, your responsibilities focus on ensuring stability, compliance, and reliability of critical banking microservices in a highly regulated environment.

---

## Key Responsibilities

### 1. Incident & Problem Management
- Monitor application health using AppDynamics, Dynatrace, Prometheus, and Grafana.
- Respond to P1/P2 incidents (regulatory reporting, compliance failures, outages) within defined SLAs.
- Troubleshoot root causes using log analysis (Splunk, ELK), database queries, and distributed tracing.
- Coordinate with L2, development, and infrastructure teams for resolution.
- Document incidents, RCA, and permanent fixes in ServiceNow/Jira.

### 2. Regulatory Compliance Operations
- Support regulatory reporting modules (CCAR, DFAST, Basel III).
- Monitor compliance workflows for AML, OFAC, and credit risk.
- Ensure audit trails and data integrity for all compliance events.
- Assist in regulatory submissions and respond to audit queries.

### 3. Microservices Health & Resilience
- Monitor service health via Eureka, Spring Boot Actuator, and Kubernetes dashboards.
- Manage circuit breakers, bulkheads, retries, and fallback logic (Resilience4j/Hystrix).
- Perform blue-green deployments, rolling upgrades, and rollback procedures.
- Automate health checks and smoke tests post-deployment.

### 4. Database & File Operations
- Perform Oracle DB health checks, monitor connection pools, and analyze slow queries.
- Support secure file uploads, regulatory submissions, and response file processing.
- Troubleshoot bulk data processing and ETL failures.

### 5. Monitoring & Observability
- Create dashboards and alerts in Grafana, Prometheus, AppDynamics, Dynatrace, and Splunk.
- Track business transactions, compliance events, and risk assessments.
- Set up automated alerting for SLA breaches, compliance violations, and system anomalies.

### 6. Change & Release Management
- Participate in CAB meetings, review change requests, and manage weekend releases.
- Validate deployments, run post-release health checks, and automate rollback scripts.
- Document release notes and update runbooks.

### 7. Automation & Scripting
- Develop advanced Unix shell scripts for log rotation, health checks, and recovery.
- Automate routine support tasks and incident response workflows.
- Maintain scripts for ETL, file processing, and compliance reporting.

### 8. On-Call & Weekend Support
- Provide 24x7 on-call support for P1/P2 issues.
- Coordinate with global teams during weekend releases and audits.
- Ensure timely communication and escalation for critical incidents.

---

## L3 Support Workflow (Step-by-Step)

1. Incident Detection
   - Monitor dashboards and alerts for anomalies.
   - Receive tickets via ServiceNow/Jira.

2. Triage & Impact Analysis
   - Assess severity (P1–P4, regulatory impact).
   - Communicate with stakeholders.

3. Troubleshooting
   - Analyze logs (Splunk, ELK).
   - Check service health (Kubernetes, Eureka).
   - Query Oracle DB for errors.

4. Resolution
   - Apply fixes (restart pods, update configs, rollback).
   - Collaborate with dev/infra teams if code changes needed.

5. Root Cause Analysis
   - Document findings, update knowledge base.
   - Suggest permanent fixes.

6. Post-Incident Review
   - Validate system health.
   - Close tickets and communicate resolution.

---

## Tools & Technologies

- Monitoring: AppDynamics, Dynatrace, Prometheus, Grafana
- Logging: Splunk, ELK Stack
- Database: Oracle 19c
- Messaging: Kafka
- Microservices: Spring Boot, Eureka, Hystrix, Resilience4j
- CI/CD: Jenkins, Docker, Kubernetes, Helm, PCF
- Ticketing: ServiceNow, Jira
- Scripting: Bash, Shell, Python

---

## Example Daily Activities

- Monitor dashboards for compliance/risk events.
- Respond to and resolve P1/P2 tickets.
- Perform health checks on microservices and databases.
- Analyze logs for errors and performance issues.
- Run and validate regulatory reports.
- Participate in CAB/change meetings.
- Automate routine checks and reporting.
- Document incidents and update runbooks.

---

## Interview Talking Points for L3 Support

- Handling P1/P2 incidents and regulatory outages.
- Troubleshooting using distributed tracing and log analysis.
- Blue-green deployment and rollback strategies.
- Creating dashboards and alerts in Grafana, Prometheus, Splunk.
- Collaborating with dev, infra, and compliance teams.
- Ensuring audit trails and regulatory compliance.
- Automating health checks and recovery scripts.
- Managing on-call and weekend support.

---

## Scenario-Based Interview Questions & Answers

**Q1: How do you troubleshoot a failed regulatory report submission?**  
A: I check the application logs in Splunk for error patterns, validate Oracle DB connectivity, and review the file upload status. If the issue is due to a service outage, I restart the affected microservice and reprocess the submission. I document the incident and communicate with compliance officers.

**Q2: How do you handle a P1 outage in the compliance workflow?**  
A: I immediately acknowledge the ticket, analyze dashboards for impacted services, and check for circuit breaker or database errors. I coordinate with the dev team for quick fixes or rollback, update stakeholders, and ensure audit logs are captured for compliance.

**Q3: How do you automate health checks and recovery?**  
A: I use Unix shell scripts to monitor service endpoints, automate log rotation, and trigger alerts for failures. Scripts also handle automated restarts and notifications to the support team.

**Q4: How do you create dashboards and alerts in Grafana and Prometheus?**  
A: I connect Prometheus as a data source in Grafana, use PromQL to query metrics, and design dashboards for compliance KPIs. I set up alerting rules for SLA breaches and share dashboards with stakeholders.

---

## Grafana & Prometheus Interview Q&A

**Q1: How do you connect Prometheus as a data source in Grafana?**  
A: In Grafana, go to "Data Sources," select Prometheus, enter the Prometheus server URL, and save. This enables querying metrics from Prometheus in Grafana dashboards.

**Q2: What is PromQL and how do you use it to query metrics?**  
A: PromQL is Prometheus Query Language. It allows you to query and aggregate metrics, e.g., `sum(rate(http_requests_total[5m])) by (service)` to get request rates per service.

**Q3: How do you create and share dashboards in Grafana?**  
A: Create a new dashboard, add panels with PromQL queries, customize visualizations, and use the "Share" option to send links or export dashboards for team use.

**Q4: How do you set up alerting in Grafana and Prometheus?**  
A: In Grafana, configure alert rules on panels, set thresholds, and notification channels (email, Slack). In Prometheus, define alerting rules in `alert.rules` and use Alertmanager for notifications.

**Q5: What are exporters in Prometheus and how do you use them?**  
A: Exporters are agents that collect metrics from services (e.g., node_exporter for system metrics, jmx_exporter for JVM). Deploy exporters alongside services and configure Prometheus to scrape their endpoints.

**Q6: How do you troubleshoot missing or delayed metrics in Grafana?**  
A: Check Prometheus scrape configs, exporter health, and network connectivity. Review Grafana panel queries and time ranges. Use Prometheus targets UI to verify endpoint status.

**Q7: How do you optimize Prometheus performance for large-scale environments?**  
A: Use federation, sharding, and remote storage. Limit scrape intervals, reduce retention periods, and optimize queries. Monitor Prometheus resource usage.

**Q8: How do you manage dashboard versioning and sharing in Grafana?**  
A: Use Grafana’s dashboard export/import features, version control via JSON files, and folders for organization. Share dashboards via links or user permissions.

**Q9: What is the difference between recording rules and alerting rules in Prometheus?**  
A: Recording rules precompute and store query results for faster access. Alerting rules evaluate conditions and trigger alerts when thresholds are met.

**Q10: How do you secure Grafana and Prometheus in a production environment?**  
A: Enable authentication (LDAP, OAuth), restrict access via firewalls, use HTTPS, and limit user permissions. Regularly update and audit configurations.

---

## Final Notes

This L3 support guide is tailored for the ORCIT-EIT application in BofA’s Risk & Compliance team. Use these points to highlight your expertise in production support, regulatory compliance, automation, monitoring, and incident management during interviews.
