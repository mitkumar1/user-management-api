This guide covers essential UNIX/Linux commands, explanations, and practical examples. Use it to prepare for technical interviews and strengthen your command-line skills.

---

###############################################################################
## 1. File and Directory Operations
###############################################################################

- `ls`  
  *List files and directories in the current directory.*
  ```sh
  ls
  ```

- `cp <source> <destination>`  
  *Copy files or directories from source to destination.*
  ```sh
  cp file.txt backup.txt
  cp -r dir1/ dir2/   # Copy directories recursively
  ```

- `mv <source> <destination>`  
  *Move or rename files and directories.*
  ```sh
  mv oldname.txt newname.txt
  mv file.txt /tmp/
  ```

- `rm <file>`  
  *Remove (delete) files.*
  ```sh
  rm unwanted.txt
  rm -r old_folder/   # Remove directories recursively
  ```

- `mkdir <directory>`  
  *Create a new directory.*
  ```sh
  mkdir new_folder
  ```

- `rmdir <directory>`  
  *Remove an empty directory.*
  ```sh
  rmdir empty_folder
  ```

- `touch <file>`  
  *Create an empty file or update the timestamp of an existing file.*
  ```sh
  touch newfile.txt
  ```

- `find <path> -name <pattern>`  
  *Search for files and directories recursively.*
  ```sh
  find . -name "*.log"
  ```

---

###############################################################################
## 2. Viewing File Content
###############################################################################

- `cat <file>`  
  *Display the content of a file.*
  ```sh
  cat notes.txt
  ```

- `less <file>`  
  *View file content one page at a time (scrollable).*
  ```sh
  less longfile.txt
  ```

- `head <file>`  
  *Show the first 10 lines of a file (use `-n` for more/less).*
  ```sh
  head logfile.txt
  head -20 logfile.txt   # First 20 lines
  ```

- `tail <file>`  
  *Show the last 10 lines of a file (use `-n` for more/less).*
  ```sh
  tail logfile.txt
  tail -20 logfile.txt   # Last 20 lines
  ```

- `nl <file>`  
  *Display file content with line numbers.*
  ```sh
  nl script.sh
  ```

---

###############################################################################
## 3. Permissions and Ownership
###############################################################################

- `chmod <permissions> <file>`  
  *Change file or directory permissions (read, write, execute).*
  ```sh
  chmod 755 script.sh
  chmod u+x myapp      # Add execute for user
  ```

- `chown <owner>:<group> <file>`  
  *Change file owner and group.*
  ```sh
  chown alice:devs project.txt
  ```

- `umask`  
  *Show or set default permissions for new files and directories.*
  ```sh
  umask
  ```

---

###############################################################################
## 4. System Information
###############################################################################

- `uname -a`  
  *Display system information (kernel, architecture, etc.).*
  ```sh
  uname -a
  ```

- `uptime`  
  *Show how long the system has been running and load averages.*
  ```sh
  uptime
  ```

- `whoami`  
  *Display the current logged-in username.*
  ```sh
  whoami
  ```

- `df -h`  
  *Show disk space usage in human-readable format.*
  ```sh
  df -h
  ```

- `du -sh <directory>`  
  *Show the size of a directory.*
  ```sh
  du -sh /var/log
  ```

- `free -m`  
  *Display memory usage in MB.*
  ```sh
  free -m
  ```

---

###############################################################################
## 5. Process Management
###############################################################################

- `ps`  
  *List running processes.*
  ```sh
  ps aux
  ```

- `top`  
  *Display real-time system tasks and resource usage.*
  ```sh
  top
  ```

- `htop`  
  *Interactive process viewer (if installed).*
  ```sh
  htop
  ```

- `kill <pid>`  
  *Terminate a process by its Process ID (PID).*
  ```sh
  kill 1234
  kill -9 1234   # Force kill
  ```

- `jobs`  
  *List background jobs in the current shell.*
  ```sh
  jobs
  ```

- `bg` / `fg`  
  *Move jobs to background/foreground.*
  ```sh
  bg %1
  fg %1
  ```

---

###############################################################################
## 6. Networking
###############################################################################

- `ifconfig`  
  *Display or configure network interfaces.*
  ```sh
  ifconfig
  ```

- `ip addr`  
  *Show IP addresses (modern alternative to ifconfig).*
  ```sh
  ip addr
  ```

- `ping <host>`  
  *Test network connectivity to another host.*
  ```sh
  ping google.com
  ```

- `netstat`  
  *Display network connections, routing tables, and interface statistics.*
  ```sh
  netstat -an
  ```

- `curl <url>`  
  *Transfer data from or to a server (test APIs, download files).*
  ```sh
  curl https://example.com
  ```

- `wget <url>`  
  *Download files from the web.*
  ```sh
  wget https://example.com/file.zip
  ```

---

###############################################################################
## 7. Text Processing
###############################################################################

- `grep <pattern> <file>`  
  *Search for a pattern in files.*
  ```sh
  grep "error" logfile.txt
  grep -r "TODO" src/   # Recursive search
  ```

- `awk '{print $1}' <file>`  
  *Process and analyze text files (print first column).*
  ```sh
  awk '{print $1}' data.txt
  ```

- `sed 's/old/new/g' <file>`  
  *Stream editor for filtering and transforming text.*
  ```sh
  sed 's/foo/bar/g' file.txt
  ```

- `sort <file>`  
  *Sort lines of text files.*
  ```sh
  sort names.txt
  ```

- `uniq <file>`  
  *Report or filter out repeated lines in a file (often used with sort).*
  ```sh
  sort names.txt | uniq
  ```

- `wc <file>`  
  *Count lines, words, and characters in a file.*
  ```sh
  wc notes.txt
  ```

---

###############################################################################
## 8. System Management
###############################################################################

- `shutdown -h now`  
  *Shut down the system immediately.*
  ```sh
  sudo shutdown -h now
  ```

- `reboot`  
  *Reboot the system.*
  ```sh
  sudo reboot
  ```

- `history`  
  *Show command history.*
  ```sh
  history
  ```

- `crontab -e`  
  *Edit the cron table to schedule recurring tasks.*
  ```sh
  crontab -e
  ```

---

**Tip:**  
Practice these commands in a Linux terminal or emulator. Understand the options and try combining commands using pipes (`|`) and redirection (`>`, `>>`) for more advanced tasks.  
For interview prep, be ready to explain what each command does and give real-world examples.

---