import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { catchError, map, Observable, of } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
    providedIn: 'root'
})
export class TokenGuard implements CanActivate {

    constructor(private readonly http: AuthService, private readonly router: Router) {}

    canActivate(): Observable<boolean> {
        return this.http.isLoggedIn()
            .pipe(catchError(() => of(true)),
        map(response => {
            if (response) {
                this.router.navigate(['home']);
                return false;
            }
            return true;
        }))
    }
}
