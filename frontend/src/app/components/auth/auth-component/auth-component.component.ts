import { Component, ElementRef, Renderer2, ViewChild } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { catchError, of, take } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth',
  standalone: false,
  templateUrl: './auth-component.component.html',
  styleUrl: './auth-component.component.scss'
})
export class AuthComponent {

  @ViewChild('passwordInput')
  password!: ElementRef<HTMLInputElement>;

  img: 'eye' | 'hidden' = 'hidden';
  err: boolean = false;
  
  constructor(private render: Renderer2, private auth: AuthService, private router: Router) { }

  togglePassword(): void {
    if (this.img === 'eye') {
      this.img = 'hidden';
      this.render.setProperty(this.password.nativeElement, 'type', 'password');
      return;
    }
    this.img = 'eye';
    this.render.setProperty(this.password.nativeElement, 'type', 'text');
  }

  onLogin(email: string): void {
    this.auth.login({
      email: email,
      password: this.password.nativeElement.value
    }).pipe(take(1), catchError(() => of(this.err = true)))
      .subscribe(() => {
        this.router.navigate(['home']);
      })
  }
  
  onRegister(): void {
    this.router.navigate(['auth/register']);
  }
}
