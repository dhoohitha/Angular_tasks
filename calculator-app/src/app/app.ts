import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Calculator } from './calculator/calculator';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Calculator],
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
})
export class App {}
