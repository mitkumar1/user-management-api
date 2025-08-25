################################################
# Bash Scripting & Unix/Linux Command Interview Cheat Sheet

## 1. Bash Basics
- **What is Bash?**  
  Bash is a Unix shell and scripting language for command execution and automation.

- **Shebang (`#!/bin/bash`):**  
  Tells the OS to use Bash to run the script.

## 2. Script Execution & Permissions
- **Make script executable:**  
  `chmod +x script.sh`
- **Run script:**  
  `./script.sh`

## 3. Variables & Parameters
- **Assign variable:**  
  `my_var="hello"`
- **Positional parameters:**  
  `$1`, `$2`, ... (arguments passed to script)

## 4. User Input
```bash
read -p "Enter your name: " name
echo "Hello, $name!"
```

## 5. Conditionals & Comparisons
- **String comparison:**  
  `[ "$a" = "$b" ]` or `[[ "$a" == "$b" ]]`
- **File exists:**  
  `[ -e file.txt ]`
- **If statement:**
  ```bash
  if [ -e file.txt ]; then
      echo "File exists"
  fi
  ```

## 6. Loops
- **For loop:**
  ```bash
  for file in *.txt; do
      echo "$file"
  done
  ```
- **While loop:**
  ```bash
  count=1
  while [ $count -le 5 ]; do
      echo "Count $count"
      ((count++))
  done
  ```

## 7. Functions
```bash
greet() {
    echo "Hello, $1!"
}
greet "World"
```

## 8. File Operations
- **Redirect output (overwrite):**  
  `echo "Hello" > file.txt`
- **Redirect output (append):**  
  `echo "Hello" >> file.txt`
- **Check if file is readable:**  
  `[ -r file.txt ]`
- **Copy file:**  
  `cp file.txt file.bak`
- **Remove empty lines:**  
  `sed '/^$/d' file.txt > newfile.txt`
- **Sort file:**  
  `sort file.txt`
- **Find files:**  
  `find /path -name "*.txt"`

## 9. Arrays
```bash
my_array=(one two three)
echo ${my_array[0]}
for i in "${my_array[@]}"; do
    echo $i
done
```

## 10. Error Handling
- **Check exit status:**  
  `if [ $? -eq 0 ]; then echo "Success"; fi`
- **Exit on error:**  
  `set -e`

## 11. Scheduling & Automation
- **Cron job:**  
  `crontab -e`  
  `0 2 * * * /path/to/script.sh` (runs daily at 2 AM)

## 12. Useful Commands
- **Grep for pattern:**  
  `grep "error" logfile.txt`
- **Disk usage:**  
  `du -sh /path`
- **Current user & hostname:**  
  `echo $USER`  
  `hostname`
- **Generate random number:**  
  `echo $RANDOM`
- **Install package:**  
  `sudo apt-get install package_name`

## 13. Advanced Topics
- **Redirect stdout & stderr:**  
  `command > output.log 2>&1`
- **Process substitution:**  
  `diff <(ls dir1) <(ls dir2)`
- **Trap signals:**  
  `trap 'echo "Cleanup"; exit' SIGINT`
- **Debug script:**  
  `set -x` (prints commands as executed)

## 14. String Manipulation
- **Length:**  
  `${#string}`
- **Substring:**  
  `${string:position:length}`
- **Uppercase:**  
  `${string^^}`

## 15. Menu Example
```bash
echo "1. Show Date"
echo "2. List Files"
echo "3. Exit"
read -p "Choose an option: " choice
case $choice in
    1) date ;;
    2) ls ;;
    3) exit 0 ;;
    *) echo "Invalid option" ;;
esac
```

## 16. Unix/Linux Command Interview Q&A (L3 Production Support)

- **What is the difference between Unix and Linux?**
  Unix is a proprietary OS; Linux is open-source and Unix-like.

- **How do you check disk usage?**
  `df -h` shows disk space; `du -sh /path` shows directory size.

- **How do you check memory usage?**
  `free -h` displays memory usage.

- **How do you monitor running processes?**
  `top` or `htop` for live process view; `ps aux` for all processes.

- **How do you find a process by name?**
  `ps aux | grep process_name`

- **How do you kill a process?**
  `kill PID` or `kill -9 PID` for force kill.

- **How do you check system uptime?**
  `uptime` command.

- **How do you check open network ports?**
  `netstat -tuln` or `ss -tuln`

- **How do you check active network connections?**
  `netstat -anp` or `ss -anp`

- **How do you check system logs?**
  `tail -f /var/log/syslog` or `tail -f /var/log/messages`

- **How do you change file permissions?**
  `chmod` command (e.g., `chmod 755 file`)

- **How do you change file ownership?**
  `chown user:group file`

- **How do you search for files?**
  `find /path -name "filename"`

- **How do you search for text in files?**
  `grep "pattern" file`

- **How do you compress and extract files?**
  `tar -czf archive.tar.gz dir/` to compress; `tar -xzf archive.tar.gz` to extract.

- **How do you check listening services?**
  `netstat -plnt` or `ss -plnt`

- **How do you check system resource usage?**
  `vmstat`, `iostat`, `sar` for CPU, memory, and disk stats.

- **How do you schedule jobs?**
  `crontab -e` to edit cron jobs.

- **How do you check environment variables?**
  `env` or `printenv`

- **How do you check file type?**
  `file filename`

- **How do you display file contents?**
  `cat`, `less`, `more` commands.

- **How do you check last login info?**
  `last` command.

- **How do you check system hostname and IP?**
  `hostname` and `ip addr` or `ifconfig`

- **How do you check running services?**
  `systemctl status service` or `service service status`

- **How do you restart a service?**
  `systemctl restart service` or `service service restart`

- **How do you check scheduled jobs?**
  `crontab -l` lists user cron jobs.

- **How do you check file size?**
  `ls -lh file`

- **How do you check directory size?**
  `du -sh dir/`

- **How do you check kernel version?**
  `uname -r`

- **How do you check OS version?**
  `cat /etc/os-release` or `lsb_release -a`

- **How do you check user info?**
  `id username` or `whoami`

- **How do you check running jobs?**
  `jobs` command (for shell jobs)

- **How do you check file permissions?**
  `ls -l file`

- **How do you check system date and time?**
  `date` command

################################################
## 16. Unix/Linux Command Reference (Section-wise)

### System Information
- `uname -a` : Show system and kernel information
- `hostname` : Show system hostname
- `cat /etc/os-release` : Show OS version
- `uptime` : Show system uptime
- `date` : Show current date and time
- `whoami` : Show current user
- `id username` : Show user info
- `last` : Show last login info

### Disk & File System
- `df -h` : Show disk space usage
- `du -sh /path` : Show directory size
- `ls -lh` : List files with size and permissions
- `find /path -name "filename"` : Search for files
- `file filename` : Show file type
- `ls -l file` : Show file permissions
- `ls -lh file` : Show file size

### Memory & CPU
- `free -h` : Show memory usage
- `top` / `htop` : Show live process and resource usage
- `vmstat` : Show system performance
- `iostat` : Show CPU and I/O stats
- `sar` : Show historical CPU/memory stats

### Processes & Jobs
- `ps aux` : List all processes
- `ps aux | grep name` : Find process by name
- `kill PID` : Kill process
- `kill -9 PID` : Force kill process
- `jobs` : List shell jobs

### Networking
- `ip addr` / `ifconfig` : Show network interfaces
- `netstat -tuln` / `ss -tuln` : Show open ports
- `netstat -anp` / `ss -anp` : Show active connections
- `ping host` : Test network connectivity
- `curl url` : Test HTTP connectivity

### Services & Scheduling
- `systemctl status service` : Check service status
- `systemctl restart service` : Restart service
- `service service status` : Check service status (SysV)
- `service service restart` : Restart service (SysV)
- `crontab -e` : Edit cron jobs
- `crontab -l` : List cron jobs

### File Operations
- `cat file` : Show file contents
- `less file` / `more file` : View file contents page-wise
- `cp src dest` : Copy file
- `mv src dest` : Move/rename file
- `rm file` : Remove file
- `chmod 755 file` : Change file permissions
- `chown user:group file` : Change file ownership
- `ln -s target link` : Create symbolic link
- `tar -czf archive.tar.gz dir/` : Compress directory
- `tar -xzf archive.tar.gz` : Extract archive

### Searching & Filtering
- `grep "pattern" file` : Search text in file
- `awk '{print $1}' file` : Print first column
- `sed 's/old/new/g' file` : Replace text in file
- `sort file` : Sort file contents
- `uniq file` : Remove duplicate lines

### System Logs
- `tail -f /var/log/syslog` : Monitor system log
- `tail -f /var/log/messages` : Monitor messages log

### Package Management
- `sudo apt-get install package` : Install package (Debian/Ubuntu)
- `sudo yum install package` : Install package (RHEL/CentOS)
- `sudo dnf install package` : Install package (Fedora)

### Environment & Variables
- `env` / `printenv` : List environment variables
- `export VAR=value` : Set environment variable

### Miscellaneous
- `echo $RANDOM` : Generate random number
- `basename /path/file.txt` : Get filename from path
- `du -sh dir/` : Show directory size
- `lsb_release -a` : Show Linux distribution info

## 17. File and Directory Management
- `ls` : List files and directories
- `ls -l` : List with details
- `ls -a` : List including hidden files
- `cd /path` : Change directory
- `pwd` : Print working directory
- `mkdir dir` : Create directory
- `rmdir dir` : Remove empty directory
- `rm file` : Remove file
- `rm -r dir` : Remove directory and contents
- `cp src dest` : Copy file
- `cp -r src_dir dest_dir` : Copy directory
- `mv src dest` : Move/rename file or directory
- `touch file` : Create empty file
- `find /path -name "filename"` : Find files
- `ln -s target link` : Create symbolic link
- `du -sh dir/` : Show directory size
- `df -h` : Show disk space usage

## 18. Text Processing
- `cat file` : Show file contents
- `less file` / `more file` : View file contents page-wise
- `head file` : Show first lines
- `tail file` : Show last lines
- `tail -f file` : Monitor file in real time
- `grep "pattern" file` : Search for text
- `awk '{print $1}' file` : Print columns/fields
- `sed 's/old/new/g' file` : Replace text
- `sort file` : Sort lines
- `uniq file` : Remove duplicate lines
- `wc -l file` : Count lines
- `cut -d':' -f1 file` : Cut fields by delimiter
- `tr 'a-z' 'A-Z' < file` : Translate characters
- `diff file1 file2` : Compare files
- `paste file1 file2` : Merge lines from files
- `rev file` : Reverse lines

## 19. Connecting to the Outside World
- `ping host` : Test network connectivity
- `curl url` : Transfer data from/to server
- `wget url` : Download files from web
- `ssh user@host` : Secure shell remote login
- `scp src user@host:dest` : Secure copy files
- `ftp host` : File transfer protocol
- `telnet host port` : Connect to remote host
- `nc host port` : Netcat for network connections
- `mail -s "subject" user@domain` : Send email from command line
- `dig domain` : DNS lookup
- `nslookup domain` : DNS query
- `traceroute host` : Trace network route
- `ifconfig` / `ip addr` : Show network interfaces

################################################

## 20. Rare/Advanced Unix Commands

### System & Process Management
- `lsof` : List open files and the processes using them
- `strace -p PID` : Trace system calls of a running process
- `dmesg` : Print kernel ring buffer messages
- `nice -n 10 command` : Run command with adjusted priority
- `renice PID` : Change priority of a running process
- `nohup command &` : Run command immune to hangups
- `screen` / `tmux` : Terminal multiplexers for persistent sessions
- `at time` : Schedule a one-time job
- `watch command` : Run command repeatedly and display output

### Disk & Filesystem
- `dd if=/dev/sda of=/backup.img` : Disk cloning and backup
- `fsck /dev/sda1` : Check and repair filesystem
- `mount /dev/sda1 /mnt` : Mount filesystem
- `umount /mnt` : Unmount filesystem
- `tune2fs -l /dev/sda1` : Show/ext2/ext3/ext4 filesystem info
- `mkfs.ext4 /dev/sdb1` : Create ext4 filesystem

### Networking & Security
- `tcpdump -i eth0` : Capture network packets
- `nmap host` : Network scanner
- `iptables -L` : List firewall rules
- `ip route` : Show routing table
- `arp -a` : Show ARP table
- `ss -s` : Socket statistics
- `openssl` : SSL/TLS and cryptography toolkit
- `gpg` : Encrypt, decrypt, and sign files

### Text/Data Processing
- `xargs` : Build and execute command lines from input
- `tee file` : Split output to file and terminal
- `sort -u file` : Sort and remove duplicates
- `comm file1 file2` : Compare two sorted files line by line
- `split -l 1000 file prefix` : Split file into pieces
- `join file1 file2` : Join lines of two files on a common field
- `column -t file` : Format text into columns
- `iconv -f utf8 -t ascii file` : Convert file encoding

### System Monitoring & Debugging
- `top -H` : Show threads
- `vmstat 1` : System performance every second
- `iotop` : Monitor disk I/O
- `pidstat` : Per-process statistics
- `sar -u 1 3` : CPU usage every second for 3 seconds
- `free -m` : Memory usage in MB
- `mpstat -P ALL 1` : Per-CPU stats

### Miscellaneous
- `yes | command` : Automatically answer 'yes' to prompts
- `bc` : Command-line calculator
- `factor number` : Print prime factors
- `seq 1 10` : Print sequence of numbers
- `cal 2025` : Display calendar for year
- `shuf -n 5 file` : Randomly select lines from file
- `timeout 10 command` : Run command with a time limit
- `time command` : Measure execution time
- `hexdump -C file` : Hexadecimal view of file
- `file -i file` : Show MIME type of file

################################################

This cheat sheet covers the most common Bash scripting and Unix/Linux command interview topics and practical commands. Use it for quick review and interview preparation!

################################################
# Bash Scripting & Unix/Linux Command Interview Cheat Sheet

## 21. Python Scripting for Production Support

### Why Use Python Scripts in Production Support?
- Python is versatile, easy to learn, and has powerful libraries for automation, monitoring, data processing, and integration.
- Enables rapid development of custom tools for log analysis, alerting, API calls, and remediation.
- Cross-platform and integrates well with Unix/Linux environments.
- Reduces manual effort, improves reliability, and supports advanced troubleshooting.

### Common Python Commands & Scripts
- `python script.py` : Run a Python script
- `python3` : Use Python 3 interpreter
- `pip install package` : Install Python package
- `import os` : Access OS functions
- `import sys` : Access system-specific parameters
- `import subprocess` : Run shell commands
- `import logging` : Logging in scripts
- `import requests` : Make HTTP requests
- `import json` : Parse JSON data
- `import csv` : Read/write CSV files
- `import re` : Regular expressions
- `with open('file.txt') as f:` : File operations
- `os.listdir('.')` : List directory contents
- `os.path.exists('file.txt')` : Check file existence
- `subprocess.run(['ls', '-l'])` : Run shell command
- `logging.basicConfig(level=logging.INFO)` : Setup logging
- `requests.get('http://example.com')` : HTTP GET request
- `json.loads(data)` : Parse JSON string
- `csv.reader(f)` : Read CSV file
- `re.search('pattern', string)` : Search with regex

### Senior L3 Support/Developer Python Interview Q&A

- **Why is Python preferred for production support automation?**
  Python is easy to write, maintain, and has rich libraries for automation, monitoring, and integration.

- **How do you handle errors in Python scripts?**
  Use try/except blocks and logging for robust error handling and diagnostics.

- **How can you monitor log files using Python?**
  Use file I/O and regex to parse logs, or libraries like `watchdog` for real-time monitoring.

- **How do you call shell commands from Python?**
  Use `subprocess.run()` or `os.system()` to execute shell commands and capture output.

- **How do you schedule Python scripts in production?**
  Use cron jobs, systemd timers, or task schedulers to run scripts at intervals.

- **How do you interact with REST APIs in Python?**
  Use the `requests` library to send HTTP requests and process responses.

- **How do you parse and process large files efficiently in Python?**
  Use generators, file streaming, and libraries like `pandas` for efficient data handling.

- **How do you ensure Python scripts are secure in production?**
  Validate inputs, handle exceptions, use secure libraries, and follow best practices for secrets management.

- **How do you package and deploy Python scripts?**
  Use virtual environments, requirements.txt, and package managers like pip for reproducible deployments.

- **How do you integrate Python scripts with monitoring tools?**
  Use APIs, SDKs, or CLI tools to send alerts, metrics, or automate responses.

################################################

This cheat sheet covers the most common Bash scripting and Unix/Linux command interview topics and practical commands. Use it for quick review and interview preparation!


