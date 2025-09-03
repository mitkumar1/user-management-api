describe('MyComponent', () => {



# Angular Interview Questions & Answers (Quick Reference)

############################################################
## Beginner Level
############################################################

### 1. What is Angular?
Angular is a TypeScript-based framework for building web applications. It supports SPA, modular architecture, and strong tooling.

### 2. Key Features
- Component-based architecture
- Two-way data binding
- Dependency injection
- Directives & Pipes
- RxJS & Observables
- Angular CLI
- Routing & navigation
- Forms (template-driven & reactive)
- Testing support

### 3. Architecture (MVC/MVVM)
Separates app into Model (data), View (UI), and Component/Controller (logic).
**Diagram:**
```
+---------+        +--------+        +----------------+
|  Model  |<-----> | View   |<-----> | Component/Logic|
+---------+        +--------+        +----------------+
  | Data            | UI             | Logic
```

### 4. Components, Modules, Services
- Components: UI pieces
- Modules: Group related code
- Services: Shared logic/data

### 5. Data Binding (one-way, two-way)
| Type           | Description                  |
|----------------|-----------------------------|
| Interpolation  | {{ value }} in template      |
| Property       | [property]="value"          |
| Event          | (event)="handler()"         |
| Two-way        | [(ngModel)]="value"         |
**Diagram:**
```
[Component] <--> [Template]
  |                |
  v                v
[Data] <--------> [UI]
```

### 6. Directives, Pipes

#### Angular Directives
Directives are special instructions in Angular templates that modify the behavior or appearance of DOM elements.

**Types of Directives:**

**Structural Directive Details:**
- `*ngIf`: Conditionally adds or removes an element from the DOM based on a boolean expression.
  - Renders the element only if the condition is true.
  - Example: `<div *ngIf="isVisible">Visible!</div>`
- `*ngFor`: Repeats an element for each item in a collection.
  - Used to render lists or tables dynamically.
  - Example: `<li *ngFor="let item of items">{{item}}</li>`
- `*ngSwitch`: Conditionally displays elements based on matching a value to multiple possible cases.
  - Used for multi-case conditional rendering.
  - Example:
    ```html
    <div [ngSwitch]="color">
      <span *ngSwitchCase="'red'">Red</span>
      <span *ngSwitchCase="'blue'">Blue</span>
      <span *ngSwitchDefault>Other</span>
    </div>
    ```

**Attribute Directive Details:**
- `ngClass`: Dynamically adds or removes CSS classes on an element.
  - Used for conditional styling.
  - Example: `<div [ngClass]="{active: isActive}"></div>`
- `ngStyle`: Dynamically sets inline styles on an element.
  - Used for dynamic style changes.
  - Example: `<span [ngStyle]="{color: 'red'}">Error</span>`
- `ngModel`: Creates two-way data binding between an input and a variable.
  - Used in forms for real-time data updates.
  - Example: `<input [(ngModel)]="username">`
**Diagram:**
   v
[DOM Element]

**Examples:**
<!-- Structural -->
<div *ngIf="isVisible">Visible!</div>
  <li *ngFor="let item of items">{{item}}</li>
</ul>

<!-- Attribute -->
<div [ngClass]="'active'" [ngStyle]="{color: 'red'}"></div>

<!-- Custom -->
<div appHighlight>Highlighted!</div>
```

### 7. Dependency Injection
Provides services to components.
**Diagram:**
```
[Service] <--- Injected --- [Component]
```

### 8. Lifecycle Hooks
| Hook                | Purpose                        |
|---------------------|--------------------------------|
| ngOnInit            | Initialization                 |
| ngOnChanges         | On input changes               |
| ngDoCheck           | Custom change detection        |
| ngAfterViewInit     | After view initialized         |
| ngOnDestroy         | Cleanup                        |
**Diagram:**

[Component Created]
  |
  v
[ngOnInit]
  |
  v
[ngOnChanges/ngDoCheck]
  |
  v
[ngAfterViewInit]
  |
  v
[ngOnDestroy]

### 9. Routing
Navigation between views.
**Diagram:**

[RouterLink] -> [Router] -> [Component View]

### 10. Forms (template-driven, reactive)
- Template-driven: Uses ngModel
- Reactive: Uses FormControl, FormGroup
**Diagram:**

[FormControl] <--> [Input]
  |
  v
[FormGroup] <--> [Form]

### 11. Observables & RxJS
Async data streams, used for HTTP, events, etc.

### 12. HttpClient
Service for HTTP requests.

### 13. Change Detection
Updates view when data changes.
**Diagram:**
```
[Data Change]
  |
  v
[Change Detection]
  |
  v
[View Update]
```

### 14. Angular CLI
Command-line tool for scaffolding, building, testing, and deploying Angular apps.

### 15. Angular vs React
| Angular         | React           |
|-----------------|----------------|
| Framework       | Library        |
| TypeScript      | JavaScript     |
| Two-way binding | One-way binding|
| RxJS            | Redux/Context  |
| CLI             | Create React App|

### 16. Performance Optimization
- Lazy loading modules
- OnPush change detection
- TrackBy in *ngFor
- AOT compilation

### 17. Testing
- Jasmine & Karma for unit tests
- Protractor/Cypress for e2e

############################################################
## Senior Level
############################################################

### 18. Decorators

- @Component: Declares a component and its metadata.
- @NgModule: Declares a module and its dependencies.
- @Injectable: Marks a class as available for DI.
**Diagram:**
```
@Component
  |
  v
[Component Class]
```

### 19. Bootstrapping

Starts the Angular app by loading the root module (AppModule).
**Diagram:**
```
[platformBrowserDynamic()]
  |
  v
[AppModule]
  |
  v
[AppComponent]
```

### 20. Modules

Organize code into cohesive blocks for maintainability and reusability.
**Diagram:**
```
[AppModule]
  |
  v
[FeatureModule]--[Component]
```

### 21. Guards

Protect routes using logic (Auth, CanActivate, CanDeactivate, etc.).
**Diagram:**
```
[User Navigation]
  |
  v
[Guard]
  |
  v
[Allow/Deny Route]
```

### 22. Interceptors

Modify HTTP requests/responses globally (logging, auth, error handling).
**Diagram:**
```
[HTTP Request]
  |
  v
[Interceptor]
  |
  v
[Server]
```

### 23. Animations

Use Angular's animation API for UI effects and transitions.
**Diagram:**
```
[State Change]
  |
  v
[Animation Trigger]
  |
  v
[UI Effect]
```

### 24. Error Handling

Use RxJS catchError, global error handler, and custom error services.
**Diagram:**
```
[HTTP/Error]
  |
  v
[catchError/operator]
  |
  v
[Handle/Error]
```

### 25. Angular Universal

Server-side rendering for Angular apps to improve SEO and performance.
**Diagram:**
```
[Angular App]
  |
  v
[Server Renders HTML]
  |
  v
[Client Receives HTML]
```

### 26. ViewChild & ContentChild

Access child components/elements in templates for direct DOM manipulation.
**Diagram:**
```
[Parent Component]
  |
  v
[ViewChild/ContentChild]
  |
  v
[Child Component/Element]
```

### 27. ngZone

Manages change detection outside Angular, useful for performance and third-party integrations.
**Diagram:**
```
[Async Task]
  |
  v
[ngZone.run()]
  |
  v
[Change Detection]
```

### 28. Custom Directives

Create reusable DOM behavior (e.g., highlight, show/hide).
**Diagram:**
```
[Directive]
  |
  v
[DOM Element]
```

### 29. Custom Pipes

Create reusable data transformations (e.g., format date, currency).
**Diagram:**
```
[Pipe]
  |
  v
[Transformed Data]
```

### 30. Schematics

Code generation tools for Angular CLI (scaffold components, modules, etc.).
**Diagram:**
```
[ng generate]
  |
  v
[Component/Module/Service]
```

### 31. Polyfills

Browser compatibility for Angular apps (support older browsers).
**Diagram:**
```
[Polyfill]
  |
  v
[Browser Feature]
```

### 32. Environment Files

Manage app config for different environments (dev, prod, test).
**Diagram:**
```
[environment.ts]
  |
  v
[App Config]
```

### 33. Lazy Loading

Load modules on demand for performance and faster initial load.
**Diagram:**
```
[AppModule]
  |
  v
[Lazy Loaded Module]
```

### 34. Ahead-of-Time (AOT) Compilation

Compiles app at build time for faster load and better security.
**Diagram:**
```
[Source Code]
  |
  v
[AOT Compiler]
  |
  v
[Optimized Bundle]
```

### 35. ChangeDetectionStrategy

Default vs OnPush for performance; OnPush only checks for input changes.
**Diagram:**
```
[ChangeDetectionStrategy]
  |
  v
[Default/OnPush]
```

### 36. TrackBy Function

Optimizes *ngFor rendering by tracking items with a unique identifier.
**Diagram:**
```
[ngFor]
  |
  v
[TrackBy Function]
  |
  v
[Efficient DOM Update]
```

### 37. Angular Material

UI component library for Angular (buttons, forms, dialogs, etc.).
**Diagram:**
```
[Angular Material]
  |
  v
[UI Components]
```

### 38. Angular Animations

Built-in animation support for UI effects and transitions.
**Diagram:**
```
[Animation Trigger]
  |
  v
[Animated Element]
```

### 39. Angular Elements

Use Angular components as custom elements (Web Components).
**Diagram:**
```
[Angular Component]
  |
  v
[Custom Element]
```

### 40. Angular Ivy

Next-gen Angular renderer for smaller bundles and faster compilation.
**Diagram:**
```
[Angular Ivy]
  |
  v
[Optimized Bundle]
```

### 41. Service Worker

PWA support for offline apps and caching.
**Diagram:**
```
[Service Worker]
  |
  v
[Offline Cache]
```

### 42. Internationalization (i18n)

Support for multiple languages and localization.
**Diagram:**
```
[i18n]
  |
  v
[Localized Content]
```

### 43. Angular Schematics

Custom code generators for CLI (create components, modules, etc.).
**Diagram:**
```
[Schematics]
  |
  v
[Generated Code]
```

### 44. Angular Workspace

Multi-project repo support for managing multiple Angular apps/libraries.
**Diagram:**
```
[Workspace]
  |
  v
[Projects/Libraries]
```

### 45. Angular Testing Utilities

TestBed, async, fakeAsync, tick, etc. for unit and integration testing.
**Diagram:**
```
[TestBed]
  |
  v
[Component/Service Test]
```

### 46. Angular DevTools

Chrome extension for debugging Angular apps (inspect components, state, etc.).
**Diagram:**
```
[Angular DevTools]
  |
  v
[Debug UI]
```

### 47. Angular Security

Sanitization, XSS protection, CSP for secure Angular apps.
**Diagram:**
```
[User Input]
  |
  v
[Sanitization]
  |
  v
[Safe Output]
```

### 48. Angular Upgrade

Upgrade from AngularJS to Angular using ngUpgrade and migration tools.
**Diagram:**
```
[AngularJS App]
  |
  v
[ngUpgrade]
  |
  v
[Angular App]
```

############################################################
## Expert Level
############################################################

### 49. Angular Universal

Server-side rendering for Angular apps to improve SEO and performance.
**Diagram:**
```
[Angular App]
  |
  v
[Server Renders HTML]
  |
  v
[Client Receives HTML]
```

### 50. Angular Elements

Use Angular components as custom elements (Web Components).
**Diagram:**
```
[Angular Component]
  |
  v
[Custom Element]
```

### 51. Angular Ivy

Next-gen Angular renderer for smaller bundles and faster compilation.
**Diagram:**
```
[Angular Ivy]
  |
  v
[Optimized Bundle]
```

### 52. Service Worker

PWA support for offline apps and caching.
**Diagram:**
```
[Service Worker]
  |
  v
[Offline Cache]
```

### 53. Internationalization (i18n)

Support for multiple languages and localization.
**Diagram:**
```
[i18n]
  |
  v
[Localized Content]
```

### 54. Angular Animations

Built-in animation support for UI effects and transitions.
**Diagram:**
```
[Animation Trigger]
  |
  v
[Animated Element]
```

### 55. Angular Material

UI component library for Angular (buttons, forms, dialogs, etc.).
**Diagram:**
```
[Angular Material]
  |
  v
[UI Components]
```

### 56. Angular CLI Schematics

Custom code generators for CLI (create components, modules, etc.).
**Diagram:**
```
[Schematics]
  |
  v
[Generated Code]
```

### 57. Angular Workspace

Multi-project repo support for managing multiple Angular apps/libraries.
**Diagram:**
```
[Workspace]
  |
  v
[Projects/Libraries]
```

### 58. Angular Testing Utilities

TestBed, async, fakeAsync, tick, etc. for unit and integration testing.
**Diagram:**
```
[TestBed]
  |
  v
[Component/Service Test]
```

### 59. Angular DevTools

Chrome extension for debugging Angular apps (inspect components, state, etc.).
**Diagram:**
```
[Angular DevTools]
  |
  v
[Debug UI]
```

### 60. Angular Security

Sanitization, XSS protection, CSP for secure Angular apps.
**Diagram:**
```
[User Input]
  |
  v
[Sanitization]
  |
  v
[Safe Output]
```

### 61. Angular Upgrade

Upgrade from AngularJS to Angular using ngUpgrade and migration tools.
**Diagram:**
```
[AngularJS App]
  |
  v
[ngUpgrade]
  |
  v
[Angular App]
```

### 62. Angular Universal

Server-side rendering for Angular apps to improve SEO and performance.
**Diagram:**
```
[Angular App]
  |
  v
[Server Renders HTML]
  |
  v
[Client Receives HTML]
```

### 63. Angular Elements

Use Angular components as custom elements (Web Components).
**Diagram:**
```
[Angular Component]
  |
  v
[Custom Element]
```

### 64. Angular Ivy

Next-gen Angular renderer for smaller bundles and faster compilation.
**Diagram:**
```
[Angular Ivy]
  |
  v
[Optimized Bundle]
```

### 65. Service Worker

PWA support for offline apps and caching.
**Diagram:**
```
[Service Worker]
  |
  v
[Offline Cache]
```

### 66. Internationalization (i18n)

Support for multiple languages and localization.
**Diagram:**
```
[i18n]
  |
  v
[Localized Content]
```

### 67. Angular Animations

Built-in animation support for UI effects and transitions.
**Diagram:**
```
[Animation Trigger]
  |
  v
[Animated Element]
```

### 68. Angular Material

UI component library for Angular (buttons, forms, dialogs, etc.).
**Diagram:**
```
[Angular Material]
  |
  v
[UI Components]
```

### 69. Angular CLI Schematics

Custom code generators for CLI (create components, modules, etc.).
**Diagram:**
```
[Schematics]
  |
  v
[Generated Code]
```

### 70. Angular Workspace

Multi-project repo support for managing multiple Angular apps/libraries.
**Diagram:**
```
[Workspace]
  |
  v
[Projects/Libraries]
```

**Tip:** Practice explaining each concept in your own words and use code snippets for clarity!
