document.addEventListener("DOMContentLoaded", function() {
    const add = (a, b) => {
        return a + b;
    };

    const num1 = 512;
    const num2 = 712;
    const result = add(num1, num2);

    document.getElementById("result").textContent = `The sum of ${num1} and ${num2} is ${result}`;
});