export interface User {
  id?: number;
  username: string;
  email: string;
  name: string;
  roles?: Role[];
}

export interface Role {
  id: number;
  name: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  name: string;
}

export interface AuthResponse {
  accessToken: string;
  tokenType: string;
}

export interface ApiResponse {
  success: boolean;
  message: string;
}
