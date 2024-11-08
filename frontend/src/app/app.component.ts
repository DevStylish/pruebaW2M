import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { StarshipComponent } from './starship/starship.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, StarshipComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'frontend';

  constructor() {}
}
