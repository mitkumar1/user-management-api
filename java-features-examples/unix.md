# UNIX/Linux Command-Line Interview Guide (Senior Developer)

This guide covers essential and advanced UNIX/Linux commands, explanations, troubleshooting scenarios, and practical examples. Use it to prepare for technical interviews and strengthen your command-line skills.

---

## 1. File and Directory Operations
- **ls**: List files and directories.
- **cp**: Copy files/directories.
- **mv**: Move/rename files.
- **rm**: Remove files/directories.
- **mkdir/rmdir**: Create/remove directories.
- **touch**: Create/update file timestamp.
- **find**: Search for files recursively.

**Example:**
```sh
find /var/log -type f -size +100M   # Find log files >100MB
```

## 2. Viewing File Content
- **cat/less/head/tail/nl**: Display file content in various ways.

**Example:**
```sh
tail -f /var/log/syslog   # Live log monitoring
```

## 3. Permissions and Ownership
- **chmod**: Change permissions.
- **chown**: Change owner/group.
- **umask**: Default permissions for new files.

**Example:**
```sh
chmod 600 secrets.txt   # Only owner can read/write
```

## 4. System Information
- **uname/uptime/whoami/df/du/free**: System, disk, and memory info.

**Troubleshooting:**
```sh
df -h | sort -k5 -r   # Find disks with highest usage
free -m               # Check for memory pressure
```

## 5. Process Management
- **ps/top/htop/kill/jobs/bg/fg**: View and manage processes.

**Scenario:**
- Find and kill a stuck process:
```sh
ps aux | grep myapp
kill -9 <PID>
```

## 6. Networking
- **ifconfig/ip/ping/netstat/curl/wget**: Network info and connectivity.

**Example:**
```sh
netstat -tulnp | grep 8080   # Find processes using port 8080
curl -I https://example.com  # Check HTTP response headers
```

## 7. Text Processing
- **grep/awk/sed/sort/uniq/wc**: Search, filter, and analyze text files.

**Example:**
```sh
grep -i error /var/log/app.log | wc -l   # Count error lines
awk '{sum+=$2} END {print sum}' sales.txt # Sum values in column 2
```

## 8. System Management
- **shutdown/reboot/history/crontab**: System control and automation.

**Example:**
```sh
crontab -l   # List scheduled tasks
```

---

## Advanced Shell Scripting & Real-World Scenarios

### Find Largest Files (Disk Troubleshooting)
```sh
find / -type f -exec du -h {} + | sort -rh | head -10
```

### Monitor CPU/Memory Usage
```sh
top -b -n1 | head -20
ps aux --sort=-%mem | head -5
```

### Bulk Rename Files
```sh
for f in *.txt; do mv "$f" "${f%.txt}.bak"; done
```

### Combine Commands (Pipes & Redirection)
```sh
grep "timeout" app.log | sort | uniq > timeouts.txt
```

---

## Troubleshooting Scenarios for Interviews
- **Disk Full:** Use `df -h`, `du -sh *`, and `find` to locate large files.
- **Process Hang:** Use `ps`, `top`, and `kill` to identify and terminate stuck processes.
- **Network Issue:** Use `ping`, `netstat`, and `curl` to diagnose connectivity.
- **Permission Denied:** Use `ls -l`, `chmod`, and `chown` to fix file access.

---

## Tips for Interview Success
- Explain what each command does and why you use it.
- Give real-world troubleshooting examples.
- Show how to combine commands for automation and efficiency.
- Be ready to write and explain simple shell scripts.
- Practice in a real Linux terminal or emulator.

---

## Summary
This guide prepares you for UNIX/Linux command-line interviews at all levels. For senior roles, focus on troubleshooting, automation, and scripting. Be ready to discuss scenarios, write scripts, and explain your approach clearly.

---