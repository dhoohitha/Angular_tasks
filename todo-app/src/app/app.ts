import { Component } from '@angular/core';
import { Todo } from './todo/todo';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [Todo],
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
})
export class App {}
