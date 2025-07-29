import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { User, LoginRequest, RegisterRequest, AuthResponse, ApiResponse } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = '/api/auth';
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    // Check if user is already logged in
    const token = localStorage.getItem('token');
    if (token) {
      this.loadUserProfile().subscribe();
    }
  }

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials)
      .pipe(
        tap(response => {
          localStorage.setItem('token', response.accessToken);
          this.loadUserProfile().subscribe();
        })
      );
  }

  register(user: RegisterRequest): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(`${this.apiUrl}/register`, user);
  }

  logout(): void {
    localStorage.removeItem('token');
    this.currentUserSubject.next(null);
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  private loadUserProfile(): Observable<User> {
    return this.http.get<User>('/api/users/profile')
      .pipe(
        tap(user => this.currentUserSubject.next(user))
      );
  }

  getUserProfile(): Observable<User> {
    return this.http.get<User>('/api/users/profile');
  }
}
