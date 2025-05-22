import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home-component/home-component.component';
import { AuthComponent } from './components/auth/auth-component/auth-component.component';
import { AuthGuard } from './interceptors/auth.guard';

const routes: Routes = [
  {path: 'auth', component: AuthComponent },
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  { path: '', redirectTo: 'auth', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
