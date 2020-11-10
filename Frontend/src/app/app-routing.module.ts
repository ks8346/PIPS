import { ResetLinkComponent } from './reset-link/reset-link.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {RegisterComponent} from './register/register.component';
import {ErrorComponent} from './error/error.component';
import {TeamComponent} from './team/team.component';

const routes: Routes = [
  {path : '',component:HomeComponent},
  {path : 'login/:id',component:HomeComponent},
  {path : 'register',component:RegisterComponent},
  {path : 'home',component:HomeComponent},
  {path : 'welcome',component:LandingPageComponent},
  {path : 'team',component:TeamComponent},
  {path : 'resetLink/:token', component:ResetLinkComponent},
  {path : '**',component:ErrorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
