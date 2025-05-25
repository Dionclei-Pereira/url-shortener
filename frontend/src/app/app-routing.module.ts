import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home-component/home-component.component';
import { AuthComponent } from './components/auth/auth-component/auth-component.component';
import { AuthGuard } from './interceptors/auth.guard';
import { TokenGuard } from './interceptors/token.guard';
import { AuthRegisterComponent } from './components/auth/auth-register/auth-register.component';
import { RedirectComponent } from './components/redirect/redirect.component';
import { NotFoundComponent } from './components/not-found/not-found.component';

const routes: Routes = [
  { path: 'g/:code', component: RedirectComponent },
  { path: 'notfound', component: NotFoundComponent },
  { path: 'auth', canActivate: [TokenGuard],
    children: [
      { path: 'login', component: AuthComponent },
      { path: 'register', component: AuthRegisterComponent },
      { path: '', redirectTo: 'login', pathMatch: 'full' }
    ]},
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: 'auth/login', pathMatch: 'full' },
  { path: '**', redirectTo: 'auth/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
