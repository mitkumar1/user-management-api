## NodeJS Interview Questions and Answers (Easy Explanation)

---

### 1. What is NodeJS?
NodeJS is a runtime environment that lets you run JavaScript code outside the browser, mainly on servers. It is fast, scalable, and great for building backend services and APIs.

**Workflow Diagram:**
```
User Request -> NodeJS Server -> JavaScript Code Execution -> Response Sent
```

**Example Code:**
```js
console.log('Hello from NodeJS!');
```

**Pseudocode:**
```
Start NodeJS
Run JavaScript code
Send output to user
```

---

### 2. What is NPM?
NPM stands for Node Package Manager. It helps you install, update, and manage libraries (called packages) for your NodeJS projects.

**Workflow Diagram:**
```
Developer -> npm install package -> package.json updated -> Package available in project
```

**Example Code:**
```sh
npm install express
```

**Pseudocode:**
```
Type 'npm install <package>'
Update dependencies in package.json

**Workflow Diagram:**
```
App Code -> require('module') -> Module Loaded -> Use Module Functions
```

**Example Code:**
```js
const os = require('os');
console.log(os.platform());
```

**Pseudocode:**
```
Import module using require
Call module functions
```

---
```


**Workflow Diagram:**
```
Project Init -> npm init -> package.json Created -> Manage Dependencies
```

**Example Code:**
```json
{
	"name": "myapp",
	"version": "1.0.0",
	"dependencies": {
		"express": "^4.18.2"
	}
}
```

**Pseudocode:**
```
Run npm init
Edit package.json for dependencies and scripts
```

---
---

### 3. Why is NodeJS single-threaded?
NodeJS uses a single thread to handle requests, but it is designed to be non-blocking and asynchronous. This means it can handle many requests at the same time without waiting for one to finish before starting another.

**Workflow Diagram:**
```
Request 1

**Workflow Diagram:**
```
User Request -> HTTP Server -> Response Sent
```

**Pseudocode:**
```
Import http module
Create server with request handler
Listen on port
Send response
```

---
Request 2
Request 3

**Workflow Diagram:**
```
Async Task -> Promise Created -> Pending -> (Resolved/Rejected) -> Handle Result
```

**Example Code:**
```js
const promise = new Promise((resolve, reject) => {
	setTimeout(() => resolve('Done!'), 1000);
});
promise.then(result => console.log(result));
```

**Pseudocode:**
```
Create promise
Do async work
Resolve or reject
Handle with then/catch
```

---
	 |      |      |
	 v      v      v

**Workflow Diagram:**
```
Incoming Events -> Event Loop -> Callback Queue -> Execute Callbacks
```

**Example Code:**
```js
setTimeout(() => console.log('Timeout!'), 0);
console.log('Main thread');
```

**Pseudocode:**
```
Add async tasks to event loop
Process callbacks when ready
Continue main thread
```

---
NodeJS Event Loop (Single Thread)
Handles all requests asynchronously

**Workflow Diagram:**
```
Request -> Express Middleware -> Route Handler -> Response
```

**Example Code:**
```js
const express = require('express');
const app = express();
app.get('/', (req, res) => res.send('Hello Express'));
app.listen(3000);
```

**Pseudocode:**
```
Import express
Define routes
Start server

**Workflow Diagram:**
```
Task 1 -> Callback -> Task 2 -> Callback -> Task 3 -> Callback ...
```

**Example Code:**
```js
fs.readFile('a.txt', (err, data) => {
	fs.readFile('b.txt', (err, data) => {
		fs.readFile('c.txt', (err, data) => {
			// ...
		});
	});
});
```

**Pseudocode:**
```
Start async task
On complete, start next async task
Repeat (nested callbacks)
```

---
**Example Code:**
```js

**Workflow Diagram:**
```
Browser Request -> Server Checks Origin -> Allow/Deny Response
```

**Example Code:**
```js
const cors = require('cors');
app.use(cors());
```

**Pseudocode:**
```
On request, check origin
If allowed, send response
Else, block request
```

---
setTimeout(() => console.log('Async task'), 1000);
console.log('Main thread');
```

**Pseudocode:**
```

**Workflow Diagram:**
```
App -> Mongoose -> MongoDB -> Query/Store Data
```

**Pseudocode:**
```
Import mongoose
Connect to database
Query or store data
```

---
Start event loop
For each request:

**Workflow Diagram:**
```
Event Occurs -> EventEmitter.emit() -> Listener Handles Event
```

**Example Code:**
```js
const EventEmitter = require('events');
const emitter = new EventEmitter();
emitter.on('greet', () => console.log('Hello!'));
emitter.emit('greet');
```

**Pseudocode:**
```
Create EventEmitter
Register event listener
Emit event
```

---
	If async, delegate and continue
	Else, process immediately

**Workflow Diagram:**
```
NodeJS: Client Request -> NodeJS Server -> Response
Angular: User -> Browser -> Angular App -> UI Rendered
```

**Pseudocode:**
```
NodeJS: Handle server logic
Angular: Handle UI logic
```

---
```


**Workflow Diagram:**
```
.env File -> dotenv Loads Variables -> process.env Access
```

**Example Code:**
```js
require('dotenv').config();
console.log(process.env.MY_VAR);
```

**Pseudocode:**
```
Install dotenv
Create .env file
Load variables with dotenv
Access with process.env
```

---
---


**Workflow Diagram:**
```
setTimeout: Wait -> Run Callback
setImmediate: Event Loop Phase Ends -> Run Callback
```

**Example Code:**
```js
setTimeout(() => console.log('Timeout'), 100);
setImmediate(() => console.log('Immediate'));
```

**Pseudocode:**
```
Schedule callback with setTimeout or setImmediate
Callback runs after delay or event loop phase
```

---
### 4. How does NodeJS handle concurrency?
NodeJS uses an event loop and callbacks to manage multiple tasks at once. While the main code runs on a single thread, I/O operations (like reading files or databases) are handled in the background, and their results are processed when ready.

**Workflow Diagram:**
```
Master Process -> Fork Workers -> Handle Requests in Parallel
```

**Example Code:**
```js
const cluster = require('cluster');
if (cluster.isMaster) {
	cluster.fork();
} else {
	console.log('Worker running');
}
```

**Pseudocode:**
```
Check if master
If master, fork workers
If worker, run server code
```

---

**Workflow Diagram:**

**Workflow Diagram:**
```
User Login -> Passport Auth -> JWT Issued -> Access Protected Routes
```

**Example Code:**
```js
const passport = require('passport');
// Setup passport strategies
```

**Pseudocode:**
```
User submits credentials
Authenticate with Passport
Issue JWT token
Check token for protected routes
```

---
```
User Requests -> Event Loop -> Async I/O -> Callback Queue -> Response
```

**Example Code:**
```js
const fs = require('fs');
fs.readFile('file.txt', (err, data) => {
	if (err) throw err;
	console.log(data.toString());
});
console.log('Reading file...');
```

**Pseudocode:**
```
Receive request
If I/O needed, start async operation
Continue processing other requests
When I/O completes, run callback
```

---

### 5. What is middleware?
Middleware is a function that runs between receiving a request and sending a response. It can be used for logging, authentication, parsing data, etc.

**Workflow Diagram:**
```
Request -> Middleware 1 -> Middleware 2 -> Controller -> Response
```

**Example Code:**
```js
const express = require('express');
const app = express();
app.use((req, res, next) => {
	console.log('Request received');
	next();
});
app.get('/', (req, res) => res.send('Hello World'));
app.listen(3000);
```

**Pseudocode:**
```
On request:
	Run middleware functions
	Pass control to next middleware or controller
	Send response
```

---

### 1. What is NodeJS?
NodeJS is a runtime environment that lets you run JavaScript code outside the browser, mainly on servers. It is fast, scalable, and great for building backend services and APIs.

### 2. What is NPM?
NPM stands for Node Package Manager. It helps you install, update, and manage libraries (called packages) for your NodeJS projects.

### 3. Why is NodeJS single-threaded?
NodeJS uses a single thread to handle requests, but it is designed to be non-blocking and asynchronous. This means it can handle many requests at the same time without waiting for one to finish before starting another.

### 4. How does NodeJS handle concurrency?
NodeJS uses an event loop and callbacks to manage multiple tasks at once. While the main code runs on a single thread, I/O operations (like reading files or databases) are handled in the background, and their results are processed when ready.

### 5. What is middleware?
Middleware is a function that runs between receiving a request and sending a response. It can be used for logging, authentication, parsing data, etc.

### 6. What is the purpose of 'require' in NodeJS?
'require' is used to import modules (built-in or custom) into your NodeJS application. Example: `const fs = require('fs');`

### 7. What is package.json?
`package.json` is a file that stores information about your NodeJS project, including dependencies, scripts, and metadata.

### 8. How do you create a simple HTTP server in NodeJS?
```js
const http = require('http');
const server = http.createServer((req, res) => {
	res.end('Hello, World!');
});
server.listen(3000);
```

### 9. What are Promises?
Promises are a way to handle asynchronous operations in JavaScript. They help avoid callback hell and make code easier to read.

### 10. What is the event loop?
The event loop is a mechanism in NodeJS that allows it to process multiple events and callbacks asynchronously, making it efficient and scalable.

### 11. What is ExpressJS?
ExpressJS is a popular NodeJS framework for building web applications and APIs. It simplifies routing, middleware, and request handling.

### 12. What is callback hell?
Callback hell happens when you have many nested callbacks, making code hard to read. You can avoid it using Promises or async/await.

### 13. What is CORS?
CORS stands for Cross-Origin Resource Sharing. It is a security feature that allows or blocks requests from different domains.

### 14. How do you connect NodeJS to MongoDB?
You can use the Mongoose library:
```js
const mongoose = require('mongoose');
mongoose.connect('mongodb://localhost:27017/mydb');
```

### 15. What is an EventEmitter?
EventEmitter is a class in NodeJS that lets you create, emit, and listen for custom events in your application.

### 16. What is the difference between NodeJS and Angular?
NodeJS is used for backend/server-side development, while Angular is a frontend framework for building user interfaces.

### 17. How do you manage environment variables in NodeJS?
You can use the `process.env` object and the `dotenv` package to manage environment variables.

### 18. What is the difference between setTimeout and setImmediate?
`setTimeout` runs a function after a set delay. `setImmediate` runs a function immediately after the current event loop phase.

### 19. What is cluster in NodeJS?
Cluster allows NodeJS to use multiple CPU cores by creating child processes, improving performance for large applications.

### 20. How do you handle authentication and authorization in NodeJS?
You can use packages like PassportJS and JWT (JSON Web Token) to implement user login (authentication) and access control (authorization).

These answers are simplified for easy explanation in interviews. Let me know if you want more advanced questions or code examples!

### 21. What is REPL in NodeJS?
REPL stands for Read, Evaluate, Print, Loop. It's a tool to quickly test and debug JavaScript code in NodeJS.

### 22. What are buffers in NodeJS?
Buffers are used to handle raw binary data, like files or network streams, in NodeJS.

### 23. What are streams in NodeJS?
Streams let you read or write data in chunks, making it efficient for large files or data transfers.

### 24. What is event-driven programming?
NodeJS uses events and callbacks to handle asynchronous tasks, making it responsive and scalable.

### 25. What is the crypto module?
Crypto module provides functions for encryption, decryption, and hashing data securely.

### 26. What is callback hell?
Callback hell is when you have too many nested callbacks, making code hard to read. Use Promises or async/await to avoid it.

### 27. What is the timers module?
The timers module lets you run code after a delay (`setTimeout`), repeatedly (`setInterval`), or immediately (`setImmediate`).

### 28. What is process.nextTick()?
`process.nextTick()` schedules a callback to run after the current operation, before any I/O events.

### 29. What are HTTP request types?
GET (read), POST (create), PUT (update), PATCH (partial update), DELETE (remove).

### 30. What is the difference between spawn() and fork()?
`spawn()` runs any command as a child process. `fork()` runs a new NodeJS process and allows communication between parent and child.

### 31. What is the passport module?
Passport is a library for adding authentication (login) to NodeJS apps.

### 32. What is body-parser?
Body-parser is middleware that parses incoming request bodies, making it easier to handle form data or JSON.

### 33. What is CORS?
CORS (Cross-Origin Resource Sharing) lets your server handle requests from other domains.

### 34. What is the tls module?
TLS module helps create secure (encrypted) network connections using SSL/TLS protocols.

### 35. Can you access the DOM in NodeJS?
No, NodeJS runs on the server and does not have access to the browser's DOM.

### 36. How do you manage packages in NodeJS?
Use npm (Node Package Manager) to install, update, and remove packages.

### 37. What is NODE_ENV?
NODE_ENV is an environment variable to set the app mode (development, production, etc.).

### 38. What is the test pyramid?
Test pyramid is a strategy: lots of unit tests, fewer integration tests, and even fewer end-to-end tests.

### 39. What is piping in NodeJS?
Piping lets you send data from one stream directly into another, like reading a file and writing it somewhere else.

### 40. What is a cluster in NodeJS?
Cluster lets NodeJS use multiple CPU cores by creating child processes, improving performance.

### 41. What are cluster methods?
`fork()` creates a child process, `isMaster` checks if it's the master, `isWorker` checks if it's a worker, `send()` sends messages, `kill()` stops a worker.

### 42. How do you manage sessions in NodeJS?
Use `express-session` to store user session data securely.

### 43. Types of API functions in NodeJS?
Asynchronous (non-blocking) and synchronous (blocking).

### 44. How to implement authentication and authorization?
Use PassportJS for authentication and JWT for authorization.

### 45. How to upload files in NodeJS?
Use Multer middleware to handle file uploads in Express apps.

### 46. NodeJS vs Python (server-side scripting)?
NodeJS is event-driven and non-blocking, great for I/O tasks. Python is thread-based, good for CPU-heavy tasks.

### 47. How to connect NodeJS to MongoDB?
Use Mongoose library to connect and interact with MongoDB databases.

### 48. How to read command line arguments?
Use `process.argv` array to access command line arguments in NodeJS.

### 49. What is Redis module?
Redis is a fast, in-memory database used for caching, storing data, and message brokering. NodeJS connects to Redis using libraries like `redis`.

### 50. What is a WebSocket?
WebSocket is a protocol for real-time, two-way communication between client and server.

### 51. What is the util module?
Util module provides helpful utility functions for debugging, formatting, and inheritance.

### 52. How to handle environment variables?
Use `process.env` and the `dotenv` package to manage environment variables.

### 53. What is the DNS module?
DNS module helps resolve domain names to IP addresses in NodeJS.

### 54. Difference between setImmediate() and setTimeout()
`setImmediate()` runs after the current event loop phase. `setTimeout()` runs after a set delay.

### 55. Why does NodeJS use the V8 engine?
V8 is fast, compiles JavaScript to machine code, and is highly optimized for performance.

### 56. What is an EventEmitter?
EventEmitter lets you create, emit, and listen for custom events in NodeJS apps.

### 57. What is middleware in Express?
Middleware are functions that run during the request-response cycle, used for logging, authentication, etc.

### 58. What is process.env?
`process.env` is an object that stores environment variables for your NodeJS app.

### 59. What is async/await?
Async/await is syntax for writing asynchronous code that looks synchronous, making it easier to read and maintain.

### 60. What is npm init?
`npm init` is a command to create a new `package.json` file for your NodeJS project.
