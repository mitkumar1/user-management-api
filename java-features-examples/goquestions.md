# 100 Golang Interview Questions and Answers (with Pseudocode)

---

### 1. What is Go programming language, and why is it used?
**Answer:**  
Go (Golang) is a statically typed, compiled language developed at Google, known for simplicity, concurrency support, and fast compilation. It's used for scalable, high-performance applications, especially in cloud and web development.

---

### 2. How do you implement concurrency in Go?
**Answer:**  
By using goroutines (lightweight threads) and channels for communication.
```go
go myFunction() // starts a goroutine
ch := make(chan int)
go func() { ch <- 42 }()
val := <-ch
```

---

### 3. How do you handle errors in Go?
**Answer:**  
Functions return an error value. Check if error is nil.
```go
val, err := someFunc()
if err != nil {
    // handle error
}
```

---

### 4. How do you implement interfaces in Go?
**Answer:**  
A type implements an interface by defining its methods.
```go
type Reader interface { Read(p []byte) (n int, err error) }
type MyReader struct{}
func (m MyReader) Read(p []byte) (int, error) { /*...*/ }
```

---

### 5. How do you optimize the performance of Go code?
**Answer:**  
- Minimize allocations, use goroutines/channels efficiently, profile code, use efficient algorithms, and avoid unnecessary copying.

---

### 6. What is the role of the "init" function in Go?
**Answer:**  
`init()` is called before main and is used for package-level initialization.

---

### 7. What are dynamic and static types of declaration of a variable in Go?
**Answer:**  
- Static: `var x int = 5`
- Dynamic (type inferred): `x := 5`

---

### 8. What is the syntax for declaring a variable in Go?
**Answer:**  
```go
var x int = 10
y := 20
```

---

### 9. What are Golang packages?
**Answer:**  
Packages are directories containing Go source files; they organize and reuse code.

---

### 10. What are the different types of data types in Go?
**Answer:**  
Numeric, boolean, string, array, slice, struct, pointer, function, interface, map, channel.

---

### 11. How do you create a constant in Go?
**Answer:**  
```go
const Pi = 3.14
```

---

### 12. What data types does Golang use?
**Answer:**  
Slice, struct, pointer, function, method, boolean, numeric, string, array, interface, map, channel.

---

### 13. Distinguish unbuffered from buffered channels.
**Answer:**  
- Unbuffered: sender/receiver block until both are ready.
- Buffered: sender blocks only if buffer is full.

---

### 14. Explain string literals.
**Answer:**  
- Raw: backticks (\`foo\`), interpreted: double quotes ("foo").

---

### 15. What is a Goroutine and how do you stop it?
**Answer:**  
A goroutine is a lightweight thread. Stop it by sending a signal via channel.
```go
done := make(chan bool)
go func() {
    for { select { case <-done: return } }
}()
done <- true
```

---

### 16. What is the syntax for creating a function in Go?
**Answer:**  
```go
func add(a int, b int) int { return a + b }
```

---

### 17. How do you create a loop in Go?
**Answer:**  
```go
for i := 0; i < 10; i++ { fmt.Println(i) }
```

---

### 18. What is the syntax for an if statement in Go?
**Answer:**  
```go
if x > 0 { fmt.Println("Positive") } else { fmt.Println("Non-positive") }
```

---

### 19. What are some benefits of using Go?
**Answer:**  
Fast compilation, concurrency, garbage collection, simple syntax, strong standard library.

---

### 20. How do you create a pointer in Go?
**Answer:**  
```go
var x int = 10
var p *int = &x
```

---

### 21. What is the syntax for creating a struct in Go?
**Answer:**  
```go
type Person struct { Name string; Age int }
```

---

### 22. How do you create an array in Go?
**Answer:**  
```go
var arr [5]int
```

---

### 23. How will you perform inheritance with Golang?
**Answer:**  
By embedding structs (composition).
```go
type Animal struct { Name string }
type Dog struct { Animal }
```

---

### 24. How do you create a slice in Go?
**Answer:**  
```go
s := make([]int, 5)
```

---

### 25. What is the difference between an array and a slice in Go?
**Answer:**  
Array: fixed size. Slice: dynamic size, flexible.

---

### 26. How do you create a map in Go?
**Answer:**  
```go
m := make(map[string]int)
```

---

### 27. How do you iterate through a map in Go?
**Answer:**  
```go
for k, v := range m { fmt.Println(k, v) }
```

---

### 28. What is a goroutine in Go?
**Answer:**  
A lightweight thread managed by Go runtime.

---

### 29. What are the looping constructs in Go?
**Answer:**  
Only `for` loop (with variations).

---

### 30. What is a channel in Go?
**Answer:**  
A typed conduit for goroutines to communicate.

---

### 31. How do you create a channel in Go?
**Answer:**  
```go
ch := make(chan int)
```

---

### 32. How do you close a channel in Go?
**Answer:**  
```go
close(ch)
```

---

### 33. How do you range over a channel in Go?
**Answer:**  
```go
for v := range ch { fmt.Println(v) }
```

---

### 34. How do you handle panics and recover from them in Go?
**Answer:**  
Use `defer`, `panic()`, and `recover()`.
```go
defer func() { if r := recover(); r != nil { fmt.Println("Recovered") } }()
panic("error")
```

---

### 35. Explain the defer statement in Golang. Give an example of a deferred function’s call.
**Answer:**  
`defer` schedules a function to run after the surrounding function returns.
```go
defer fmt.Println("Done")
```

---

### 36. How do you create and use a package in Go?
**Answer:**  
Create a directory, add `.go` files with `package` declaration, then `import` in other files.

---

### 37. What is the difference between a package and a module in Go?
**Answer:**  
Package: code organization unit. Module: collection of packages with versioning.

---

### 38. How do you create a custom type in Go?
**Answer:**  
```go
type Age int
```

---

### 39. What is the syntax for type casting in Go?
**Answer:**  
```go
var x int = 10
var y float64 = float64(x)
```

---

### 40. How do you use the "blank identifier" in Go?
**Answer:**  
Use `_` to ignore a value.
```go
val, _ := someFunc()
```

---

### 41. How do you create and use a pointer to a struct in Go?
**Answer:**  
```go
p := &Person{Name: "Alice"}
fmt.Println(p.Name)
```

---

### 42. How do you embed a struct in Go?
**Answer:**  
```go
type Address struct { City string }
type Employee struct { Name string; Address }
```

---

### 43. How do you create and use a function closure in Go?
**Answer:**  
```go
func adder() func(int) int {
    sum := 0
    return func(x int) int { sum += x; return sum }
}
```

---

### 44. What is the syntax for creating and using a function literal in Go?
**Answer:**  
```go
f := func(x int) int { return x * x }
fmt.Println(f(5))
```

---

### 45. How do you use the "select" statement in Go?
**Answer:**  
```go
select {
case v := <-ch1: fmt.Println(v)
case v := <-ch2: fmt.Println(v)
default: fmt.Println("No data")
}
```

---

### 46. What is the syntax for creating and using a type assertion in Go?
**Answer:**  
```go
var i interface{} = "hello"
s := i.(string)
```

---

### 47. What is the syntax for creating and using a type switch in Go?
**Answer:**  
```go
switch v := i.(type) {
case int: fmt.Println("int")
case string: fmt.Println("string")
}
```

---

### 48. What is the syntax for creating and using a type conversion in Go?
**Answer:**  
```go
var x int = 10
var y float64 = float64(x)
```

---

### 49. How do you use the "sync" package to protect shared data in Go?
**Answer:**  
Use `sync.Mutex`.
```go
var mu sync.Mutex
mu.Lock()
// critical section
mu.Unlock()
```

---

### 50. How do you use the "sync/atomic" package to perform atomic operations in Go?
**Answer:**  
```go
import "sync/atomic"
var n int32
atomic.AddInt32(&n, 1)
```

---

### 51. How do you use the "context" package to carry around request-scoped values in Go?
**Answer:**  
```go
ctx := context.Background()
ctx = context.WithValue(ctx, "key", "value")
val := ctx.Value("key")
```

---

### 52. How do you use the "net/http" package to build an HTTP server in Go?
**Answer:**  
```go
http.HandleFunc("/", handler)
http.ListenAndServe(":8080", nil)
```

---

### 53. How do you use the "encoding/json" package to parse and generate JSON in Go?
**Answer:**  
```go
data, _ := json.Marshal(obj)
json.Unmarshal(data, &obj)
```

---

### 54. How do you use the "reflect" package to inspect the type and value of a variable in Go?
**Answer:**  
```go
t := reflect.TypeOf(x)
v := reflect.ValueOf(x)
```

---

### 55. How do you use the "testing" package to write unit tests in Go?
**Answer:**  
Create `*_test.go` files, write functions like `func TestXxx(t *testing.T)`.
```go
func TestAdd(t *testing.T) {
    if Add(2, 2) != 4 { t.Fail() }
}
```

---

### 56. How do you use the "errors" package to create and manipulate errors in Go?
**Answer:**  
```go
err := errors.New("error message")
```

---

### 57. How do you use the "net" package to implement networking protocols in Go?
**Answer:**  
```go
conn, _ := net.Dial("tcp", "localhost:8080")
```

---

### 58. How do you use the "time" package to handle dates and times in Go?
**Answer:**  
```go
now := time.Now()
fmt.Println(now.Format("2006-01-02"))
```

---

### 59. How do you use the "math" and "math/rand" packages to perform mathematical and statistical operations in Go?
**Answer:**  
```go
import "math"
x := math.Sqrt(16)
import "math/rand"
y := rand.Intn(100)
```

---

### 60. How do you use the "crypto" package to perform cryptographic operations in Go?
**Answer:**  
```go
import "crypto/sha256"
h := sha256.New()
h.Write([]byte("data"))
sum := h.Sum(nil)
```

---

### 61. How do you use the "os" package to interact with the operating system in Go?
**Answer:**  
```go
file, _ := os.Open("file.txt")
defer file.Close()
```

---

### 62. How do you use the "bufio" package to read and write buffered data in Go?
**Answer:**  
```go
reader := bufio.NewReader(file)
line, _ := reader.ReadString('\n')
```

---

### 63. How do you use the "strings" package to manipulate strings in Go?
**Answer:**  
```go
s := strings.ToUpper("hello")
```

---

### 64. How do you use the "bytes" package to manipulate byte slices in Go?
**Answer:**  
```go
b := []byte("hello")
b = bytes.ToUpper(b)
```

---

### 65. How do you use the "encoding/binary" package to encode and decode binary data in Go?
**Answer:**  
```go
binary.Write(buf, binary.LittleEndian, data)
binary.Read(buf, binary.LittleEndian, &data)
```

---

### 66. How do you use the "compress/gzip" package to compress and decompress data using the gzip algorithm in Go?
**Answer:**  
```go
w := gzip.NewWriter(file)
w.Write([]byte("data"))
w.Close()
```

---

### 67. How do you use the "database/sql" package to access a SQL database in Go?
**Answer:**  
```go
db, _ := sql.Open("mysql", "user:pass@/dbname")
rows, _ := db.Query("SELECT * FROM table")
```

---

### 68. How do you use the "html/template" package to generate HTML templates in Go?
**Answer:**  
```go
tmpl, _ := template.ParseFiles("template.html")
tmpl.Execute(w, data)
```

---

### 69. Explain byte and rune types and how they are represented.
**Answer:**  
- `byte` is an alias for `uint8` (8-bit).
- `rune` is an alias for `int32` (Unicode code point).

---

### 70. You have developed a Go program on Linux and want to compile it for both Windows and Mac. Is it possible?
**Answer:**  
Yes, use cross-compilation:
```sh
GOOS=windows go build main.go
GOOS=darwin go build main.go
```

---

### 71. How can you compile a Go program for Windows and Mac?
**Answer:**  
Set `GOOS` and `GOARCH` environment variables before building.

---

### 72. Starting from an empty file, how would you create a Go program’s basic structure? Annotate the most important parts of the source code using comments.
**Answer:**  
```go
package main // entry package
import "fmt" // import package
func main() { // entry point
    fmt.Println("Hello, World!")
}
```

---

### 73. What is the string data type in Golang, and how is it represented?
**Answer:**  
A sequence of bytes, immutable, UTF-8 encoded.

---

### 74. How can you format the Go source code in an idiomatic way?
**Answer:**  
Use `gofmt` tool.

---

### 75. Can you change a specific character in a string?
**Answer:**  
No, strings are immutable. Convert to a slice, modify, then convert back.

---

### 76. How can you concatenate string values? What happens when concatenating strings?
**Answer:**  
Use `+` operator. New string is created.

---

### 77. Give an example of an array and slice declaration.
**Answer:**  
```go
var arr [5]int
var s []int
```

---

### 78. Explain the backing array of a slice value.
**Answer:**  
A slice references an underlying array; changes to the slice affect the array.

---

### 79. Explain the Golang map type and its advantages.
**Answer:**  
Map is a key-value store, allows fast lookups, dynamic size.

---

### 80. What is the recommended Golang package for basic operations on files? What other Golang packages are used to work with files?
**Answer:**  
`os` for basic ops, `io/ioutil` for higher-level, `path/filepath` for paths.

---

### 81. Explain the object-oriented architecture of Golang.
**Answer:**  
Uses structs and methods (no classes/inheritance), supports composition.

---

### 82. What is a struct type? Can you change the struct definition at runtime?
**Answer:**  
Struct is a composite type. No, definition is static.

---

### 83. What are anonymous structs and anonymous struct fields? Give an example of such a struct declaration.
**Answer:**  
Structs without a name or embedded fields.
```go
person := struct { Name string }{ Name: "Bob" }
```

---

### 84. Explain the defer statement in Golang. Give an example of a deferred function’s call.
**Answer:**  
See Q35.

---

### 85. Write a Golang program that declares a string variable, prints the address of the variable, declares another int variable, and a pointer to it.
**Answer:**  
```go
str := "Hello"
fmt.Printf("%p\n", &str)
num := 42
ptr := &num
```

---

### 86. What are the advantages of passing pointers to functions?
**Answer:**  
Efficiency (no copy), allows modification, shared memory.

---

### 87. What are Golang methods?
**Answer:**  
Functions with a receiver type, associated with structs.

---

### 88. Create a Go program that defines a named type and a method (receiver function) for that type.
**Answer:**  
```go
type Person struct { Name string }
func (p Person) Greet() { fmt.Println("Hello,", p.Name) }
```

---

### 89. How can you ensure a Go channel never blocks while sending data to it?
**Answer:**  
Use a buffered channel or select with default.

---

### 90. Explain why concurrency is not parallelism?
**Answer:**  
Concurrency: tasks progress independently. Parallelism: tasks run at the same time.

---

### 91. What is a data race?
**Answer:**  
Concurrent access to shared data without synchronization.

---

### 92. How can you detect a data race in Go code?
**Answer:**  
Run with `go run -race` or `go test -race`.

---

### 93. What is a Go channel? What operations are available on the channel type?
**Answer:**  
A conduit for goroutines. Send, receive, close.

---

### 94. How can you ensure that a goroutine in Go receives data from a channel without blocking indefinitely if there's no data available, and without terminating prematurely if the channel is still producing data?
**Answer:**  
Use `select` with a default case.

---

### 95. Write a simple Golang program that uses a goroutine and a channel.
**Answer:**  
```go
ch := make(chan string)
go func() { ch <- "Hello" }()
fmt.Println(<-ch)
```

---

### 96. Write a program to find the sum of all even numbers from 1 to 100.
**Answer:**  
```go
sum := 0
for i := 2; i <= 100; i += 2 { sum += i }
fmt.Println(sum)
```

---

### 97. Write a program to find the largest and smallest elements in an array.
**Answer:**  
```go
arr := []int{3, 7, 2, 9}
min, max := arr[0], arr[0]
for _, v := range arr {
    if v < min { min = v }
    if v > max { max = v }
}
```

---

### 98. Write a program to check if a number is prime.
**Answer:**  
```go
n := 17
isPrime := true
for i := 2; i*i <= n; i++ {
    if n%i == 0 { isPrime = false; break }
}
```

---

### 99. Write a program to calculate the factorial of a number using recursion.
**Answer:**  
```go
func fact(n int) int {
    if n == 0 { return 1 }
    return n * fact(n-1)
}
```

---

### 100. Write a program to count the number of words in a given string.
**Answer:**  
```go
s := "hello world"
words := strings.Fields(s)
fmt.Println(len(words))
```

---