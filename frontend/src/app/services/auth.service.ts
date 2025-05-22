import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequest } from '../interfaces/auth-service/login-request.interface';
import { Observable, of, tap } from 'rxjs';
import { LoginResponse } from '../interfaces/auth-service/login-response.interface';
import { registerRequest } from '../interfaces/auth-service/register-request.interface';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private apiUrl = 'http://backend:8080/auth'

  constructor(private readonly http: HttpClient, private readonly router: Router) { }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials)
      .pipe(
        tap(response => {
          localStorage.setItem('token', response.token);
        })
      );
  }

  register(data: registerRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, data);
  }

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/auth'])
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): Observable<boolean> {
    const token = this.getToken();
    if (!token) return of(false);
    return this.http.get<boolean>(`${this.apiUrl}/isvalid`, {
      headers: { Authorization: `${token}` },
    });
  }
}
