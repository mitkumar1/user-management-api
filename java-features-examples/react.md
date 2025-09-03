# ReactJS Interview Questions & Answers (Quick Reference)

## 1. What is ReactJS?
A JavaScript library for building reusable UI components, mainly for single-page applications (SPA). Uses JSX syntax.

**Key Features:**
- Virtual DOM: Efficient UI updates.
- Component-Based: Modular, reusable code.
- Hooks: State and side effects in functional components.
- SSR: Server-side rendering for SEO.
- React Router: SPA navigation.

---

## 2. Latest Version
React v19.1.0 (March 2025).

---

## 3. MVC Architecture

Separates app into Model (data), View (UI), Controller (logic).

**Workflow Diagram:**

```
	+---------+        +--------+        +-----------+
	|  Model  |<-----> | View   |<-----> | Controller|
	+---------+        +--------+        +-----------+
		| Data            | UI             | Logic
```

Model: Handles data and business logic.
View: Displays UI to the user.
Controller: Manages user input and updates Model/View.

---

## 4. Building Blocks
- Components: UI pieces.
- JSX: HTML in JS.
- Props: Data from parent.
- State: Local data.
- Context: Global data.
- Virtual DOM: Fast, efficient updates.

---

## 5. Props vs State

| Props | State |
|-------|-------|
| Passed from parent | Local to component |
| Immutable | Mutable |
| Read-only | Read/write |

---

## 6. Virtual DOM

In-memory copy of real DOM. React updates only changed parts for speed.

**Workflow Diagram:**

```
  [Component State Change]
	   |
	   v
   +-------------------+
   |  Virtual DOM      |
   +-------------------+
	   |
	   v
   [Diff Algorithm]
	   |
	   v
   [Update Real DOM]
```

1. State/props change triggers re-render.
2. React creates a new Virtual DOM tree.
3. Diff algorithm compares new and old trees.
4. Only changed parts are updated in the real DOM.

---

## 7. Real DOM vs Virtual DOM

| Real DOM | Virtual DOM |
|----------|-------------|
| Actual browser DOM | In-memory copy |
| Slow updates | Fast updates |
| Direct manipulation | Batched, minimal changes |

---

## 8. JSX Example

```jsx
const name = "Learner";
const element = <h1>Hello, {name}!</h1>;
```

---

## 9. Component Types
- Functional: Simple JS functions, use hooks.
Functional components are JavaScript functions that return JSX. They are simple, easy to test, and use React Hooks (like useState, useEffect) for state and side effects.

Example:
function Welcome(props) {
  return <h1>Hello, {props.name}</h1>;
}

- Class: ES6 classes, use lifecycle methods.
Class components are ES6 classes that extend React.Component. They use lifecycle methods (constructor, render, componentDidMount, etc.) and manage state with this.state.

Example:

class Welcome extends React.Component {
  render() {
    return <h1>Hello, {this.props.name}</h1>;
  }
}
Workflow Diagram:
Functional Component:
  [function MyComponent(props)]
         |
         v
  [Hooks: useState, useEffect]
         |
         v
  [Render JSX]

Class Component:
  [class MyComponent extends React.Component]
         |
         v
  [Lifecycle Methods: constructor, render, componentDidMount, etc.]
         |
         v
  [Render JSX]
## 10. Browsers & JSX
Browsers can't read JSX directly. Babel transpiles JSX to JavaScript.

---

## 11. Create React App & Hello World

```sh
npm create vite@latest
cd app-name
npm start
```
```jsx
function App() {
	return <div>Hello World!</div>;
}
export default App;
```

---

## 12. Events

```jsx
<button onClick={() => alert('Clicked!')}>Click Me</button>
```

---

## 13. Lists & Keys

```jsx
const nums = [1,2,3];
<ul>{nums.map(n => <li key={n}>{n}</li>)}</ul>
```

---

## 14. Keys in React
Unique identifier for list items to help React track changes.

---

## 15. Comments

```jsx
// Single line
/* Multi-line */
```

---

## 16. React vs Angular

| React | Angular |
|-------|--------|
| Library | Framework |
| Virtual DOM | Real DOM |
| One-way data | Two-way data |

---

## 17. Render Method

Displays JSX in the browser.

**Explanation:**
The render method is used in class components to return JSX, which React converts to real DOM elements and displays in the browser. In functional components, returning JSX from the function serves the same purpose.

**Workflow Diagram:**
```
[Component]
	|
	v
[render() or return JSX]
	|
	v
[JSX -> React.createElement]
	|
	v
[Virtual DOM]
	|
	v
[Real DOM]
```

---

## 18. State

Local data storage for a component.

**Explanation:**
State is an object managed within a component that determines how the component behaves and renders. Changing state triggers a re-render.

**Workflow Diagram:**
```
[Initial State]
	|
	v
[User/Event updates state]
	|
	v
[Component re-renders with new state]
```

---

## 19. Props

Data passed from parent to child component.

**Explanation:**
Props (short for properties) are read-only data passed from a parent component to a child component. They allow components to be reusable and dynamic.

**Workflow Diagram:**
```
[Parent Component]
	|
	v
[Child Component (receives props)]
```

---

## 20. Higher Order Component (HOC)

Function that takes a component and returns a new one.

**Explanation:**
A Higher Order Component (HOC) is a function that takes a component and returns a new component with enhanced behavior or additional props. HOCs are used for code reuse, logic abstraction, and manipulating props.

**Workflow Diagram:**
```
[Original Component]
	|
	v
[HOC (adds logic/props)]
	|
	v
[Enhanced Component]
```

---

## 21. Functional vs Class Component

| Functional | Class |
|------------|-------|
| JS function | ES6 class |
| Hooks | Lifecycle methods |
| No render() | Uses render() |

---

## 22. One-way Data Binding
Data flows from parent to child only.

---

## 23. Context API

```jsx
const MyContext = React.createContext();
<MyContext.Provider value={data}>...</MyContext.Provider>
```

---

## 24. shouldComponentUpdate
Controls re-rendering in class components.

---

## 25. dangerouslySetInnerHTML
Sets raw HTML inside a component (use with caution).

---

## 26. Pure Components
Only re-render if props/state change.

---

## 27. setState
Updates state and triggers re-render.

---

## 28. Conditional Rendering

```jsx
{isLoggedIn ? <Logout /> : <Login />}
```

---

## 29. React Router

```sh
npm i react-router-dom
```

---

## 30. Router Components
- BrowserRouter: Parent router
- Switch: Renders first match
- Route: Defines path/component
- Link: Navigation

---

## 31. Routing vs Conventional Routing

| React Routing | Conventional Routing |
|---------------|---------------------|
| SPA, no reload | MPA, reloads page |
| Client-side | Server-side |

---

## 32. Lifecycle Methods

- Mounting: constructor, render, componentDidMount
- Updating: render, componentDidUpdate
- Unmounting: componentWillUnmount

**Workflow Diagram:**

```
Mounting:
	constructor -> render -> componentDidMount

Updating:
	render -> componentDidUpdate

Unmounting:
	componentWillUnmount
```

---

## 33. Mounting Methods

- componentWillMount: Before mount
- componentDidMount: After mount

---

## 34. setState Function
Updates part of the state.

---

## 35. Refs

```jsx
const myRef = useRef();
```

---

## 36. Hooks
Functions for state and side effects in functional components.

---

## 37. useState Hook

```jsx
const [count, setCount] = useState(0);
```

---

## 38. useEffect Hook

```jsx
useEffect(() => { /* runs on update */ }, [count]);
```

---

## 39. Fragments

```jsx
<>
	<h2>Title</h2>
	<p>Text</p>
</>
```

---

## 40. React Developer Tools
Chrome extension for debugging React apps.

---

## 41. Styling

```jsx
import styles from './App.module.css';
import styled from 'styled-components';
const Button = styled.button`width:100px;`;
```

---

## 42. Prop Drilling
Passing data through many layers of components.

---

## 43. Controlled Components (Forms)

```jsx
const [val, setVal] = useState('');
<input value={val} onChange={e => setVal(e.target.value)} />
```

---

## 44. Custom Hooks

```jsx
function useCustomHook() { /* ... */ }
```

---

## 45. Performance Optimization

- React.memo: Prevents unnecessary re-renders.
- Lazy loading: `React.lazy()`
- useMemo/useCallback: Memoize values/functions.

---

## 46. Strict Mode

```jsx
<React.StrictMode>
	<App />
</React.StrictMode>
```

---

## 47. Redux
Centralized state management for large apps.

---

## 48. React-Redux Benefits

- Central store
- Easier debugging
- Persistent state

---

## 49. Redux Core Components

- Store: Holds state
- Actions: Describe changes
- Reducers: Update state

---

## 50. Combine Reducers

```jsx
import { combineReducers } from "redux";
const rootReducer = combineReducers({ books, activeBook });
```

---

## 51. CORS in React
Allows requests to other domains (use axios or fetch).

---

## 52. Axios Example

```jsx
import axios from 'axios';
axios.get('/api/data').then(res => setData(res.data));

## 53. Counter Example

```jsx
import React, { useState } from "react";
const App = () => {
	const [counter, setCounter] = useState(0);
	return (
		<div>
			{counter}
			<button onClick={() => setCounter(counter + 1)}>Increment</button>
			<button onClick={() => setCounter(counter - 1)}>Decrement</button>
		</div>
	);
};
export default App;

## 54. Forms Example

```jsx
import React, { useState } from 'react';
function MyForm() {
	const [name, setName] = useState('');
	const handleSubmit = e => {
		e.preventDefault();
		console.log(name);
	};
	return (
		<form onSubmit={handleSubmit}>
			<input value={name} onChange={e => setName(e.target.value)} />
			<button type="submit">Submit</button>
		</form>
	);
}
export default MyForm;
```

---

## 55. Lazy Loading

```jsx
const LazyComp = React.lazy(() => import('./LazyComp'));
```

---

## 56. React vs React Native

| React | React Native |
|-------|--------------|
| Web apps | Mobile apps |
| HTML/CSS | Native components |

---

## 57. Memoization

- React.memo: Prevents re-renders if props unchanged.
- useMemo: Memoizes values.

---

## 58. Reconciliation
Efficiently updates DOM using Virtual DOM diffing.

---

## 59. React-Material UI
Component library for React, easy customization and rapid development.

---

## 60. Flux Architecture in Redux

Unidirectional data flow: Actions → Store → View.

**Workflow Diagram:**

```
	[Action] ---> [Dispatcher] ---> [Store] ---> [View]
```

1. User interacts with View.
2. Action is dispatched.
3. Dispatcher sends action to Store.
4. Store updates state.
5. View re-renders with new state.

---

## 61. useRef vs createRef

| useRef | createRef |
|--------|-----------|
| Hook | Function |
| Same ref on re-render | New ref on re-render |
| Functional components | Class components |

---

## 62. Custom Hooks
Reusable logic using hooks.

---

## 63. Optimizing React Performance
- Use React.memo, lazy loading, useMemo, useCallback, avoid unnecessary state updates.

---

## 64. Strict Mode
Helps find problems in development.

---

## 65. Redux with React
Global state management using actions, reducers, and store.

---

## 66. Concurrency in React
Concurrent Mode allows React to interrupt rendering and prioritize tasks.

---

## 67. Server-Side Rendering (SSR)
Render React on server, send HTML to client for faster load and SEO.

---

## 68. Forms in React
Controlled and uncontrolled components for user input.

---

## 69. Creating Forms

```jsx
import React, { useState } from 'react';
function MyForm() {
	const [name, setName] = useState('');
	const handleSubmit = e => {
		e.preventDefault();
		console.log(name);
	};
	return (
		<form onSubmit={handleSubmit}>
			<input value={name} onChange={e => setName(e.target.value)} />
			<button type="submit">Submit</button>
		</form>
	);
}
export default MyForm;
```

---

## 70. Reconciliation in React

Efficient DOM updates using Virtual DOM diffing and Fiber algorithm.

**Workflow Diagram:**

```
  [Old Virtual DOM]
	  |
	  v
  [Diff Algorithm]
	  |
	  v
  [Fiber Reconciliation]
	  |
	  v
  [Update Real DOM]
```

Fiber enables React to split rendering work into units, making updates more efficient and interruptible.

---


---

**Tip:** Practice explaining each concept in your own words and use code snippets for clarity!
