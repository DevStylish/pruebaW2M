import { Routes } from '@angular/router';
import { StarshipComponent } from './starship/starship.component';
import { StarshipFormComponent } from './starship/form/starship-form/starship-form.component';

export const routes: Routes = [
  { path: 'starships', component: StarshipComponent },
  { path: 'starships/create', component: StarshipFormComponent },
  { path: 'starships/edit/:id', component: StarshipFormComponent },
  { path: '', redirectTo: '/starships', pathMatch: 'full' },
];
