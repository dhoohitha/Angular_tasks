import { Component } from '@angular/core';
import { Movies } from './movies/movies';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [Movies],
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
})
export class App {}
