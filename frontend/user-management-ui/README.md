# User Management System - Angular 16 Frontend

A modern, responsive Angular 16 frontend application for the Spring Boot User Management API with JWT authentication and Material Design components.

## Features

- 🔐 **JWT Authentication** - Secure login/logout with token-based authentication
- 👤 **User Registration** - Create new user accounts with validation
- 📱 **Responsive Design** - Mobile-first design using Angular Material
- 🎨 **Material Design** - Beautiful UI components from Angular Material
- 🛡️ **Route Guards** - Protected routes for authenticated users
- 🔄 **HTTP Interceptors** - Automatic token attachment and error handling
- 📊 **Dashboard** - User dashboard with role-based features
- 👥 **Profile Management** - View and manage user profiles
- 🎯 **Role-based Access** - Different features for admin and regular users

## Prerequisites

Before you begin, ensure you have the following installed:

- [Node.js](https://nodejs.org/) (v16 or higher)
- [npm](https://www.npmjs.com/) (comes with Node.js)
- [Angular CLI](https://angular.io/cli) v16

## Installation

1. **Install Node.js and npm**
   - Download from https://nodejs.org/
   - Choose the LTS version
   - Verify installation: `node --version` and `npm --version`

2. **Install Angular CLI**
   ```bash
   npm install -g @angular/cli@16
   ```

3. **Navigate to the frontend directory**
   ```bash
   cd frontend/user-management-ui
   ```

4. **Install dependencies**
   ```bash
   npm install
   ```

## Configuration

The application is configured to proxy API requests to the Spring Boot backend running on port 8080. The proxy configuration is in `proxy.conf.json`.

### Backend API Requirements

Ensure your Spring Boot backend is running on `http://localhost:8080` with the following endpoints:

- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `GET /api/users/profile` - Get user profile (requires authentication)

## Running the Application

1. **Start the Angular development server**
   ```bash
   npm start
   ```
   or
   ```bash
   ng serve
   ```

2. **Open your browser**
   Navigate to `http://localhost:4200`

3. **Ensure backend is running**
   Make sure your Spring Boot application is running on `http://localhost:8080`

## Building for Production

```bash
ng build --configuration production
```

The build artifacts will be stored in the `dist/` directory.

## Project Structure

```
src/
├── app/
│   ├── components/          # Angular components
│   │   ├── dashboard/       # Dashboard component
│   │   ├── login/          # Login component
│   │   ├── navbar/         # Navigation component
│   │   ├── profile/        # Profile component
│   │   └── register/       # Registration component
│   ├── guards/             # Route guards
│   │   └── auth.guard.ts   # Authentication guard
│   ├── interceptors/       # HTTP interceptors
│   │   └── auth.interceptor.ts  # JWT token interceptor
│   ├── models/             # TypeScript interfaces
│   │   └── user.model.ts   # User and auth models
│   ├── services/           # Angular services
│   │   └── auth.service.ts # Authentication service
│   ├── app-routing.module.ts  # Route configuration
│   ├── app.component.*     # Root component
│   └── app.module.ts       # Main module
├── assets/                 # Static assets
├── styles.scss             # Global styles
└── index.html             # Main HTML file
```

## Components Overview

### 🏠 Dashboard Component
- Welcome screen for authenticated users
- Role-based content display
- Quick action cards
- Admin panel access (for admin users)

### 🔐 Login Component
- User authentication form
- Form validation
- Error handling
- Redirect to registration

### 📝 Register Component
- User registration form
- Password confirmation
- Email validation
- Success/error feedback

### 👤 Profile Component
- Display user information
- Show user roles and permissions
- Role-based features
- Edit profile functionality

### 🧭 Navbar Component
- Navigation menu
- User menu with logout
- Responsive design
- Context-aware display

## Features in Detail

### Authentication Flow
1. User logs in with username/password
2. Backend returns JWT token
3. Token stored in localStorage
4. HTTP interceptor adds token to API requests
5. Route guard protects authenticated routes

### Form Validation
- Real-time validation feedback
- Custom validators for password matching
- Material Design error messages
- Disabled submit buttons until valid

### Responsive Design
- Mobile-first approach
- Flexible grid layouts
- Adaptive navigation
- Touch-friendly interactions

## API Integration

The frontend integrates with your Spring Boot backend through:

- **AuthService**: Handles login, registration, and user profile
- **HTTP Interceptor**: Automatically adds JWT tokens
- **Proxy Configuration**: Routes `/api/*` requests to backend

## Styling

- **Angular Material**: Primary UI component library
- **SCSS**: Enhanced CSS with variables and mixins
- **Responsive Grid**: CSS Grid and Flexbox layouts
- **Material Icons**: Google Material Design icons

## Development Tips

1. **Hot Reload**: Changes are automatically reflected in the browser
2. **DevTools**: Use Angular DevTools browser extension
3. **Debugging**: Console logs and network tab for API calls
4. **Testing**: Run `ng test` for unit tests

## Common Issues

1. **CORS Errors**: Ensure backend allows requests from `http://localhost:4200`
2. **API Not Found**: Verify backend is running on port 8080
3. **Token Expired**: Check JWT token expiration in backend
4. **Route Not Found**: Verify routing configuration

## Next Steps

To extend the application:

1. **Add Admin Features**: User management, role assignment
2. **Profile Editing**: Update user information
3. **Password Reset**: Forgot password functionality
4. **Email Verification**: Account verification process
5. **Audit Logs**: Track user activities

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is part of the User Management System and follows the same license as the parent project.
