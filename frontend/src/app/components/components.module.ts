import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login-component/login-component.component';
import { HomeComponent } from './home/home-component/home-component.component';

@NgModule({
    declarations: [
        LoginComponent,
        HomeComponent
    ],
    imports: [ CommonModule ],
    exports: [
        LoginComponent,
        HomeComponent
    ],
    providers: [],
})
export class ComponentsModule {}