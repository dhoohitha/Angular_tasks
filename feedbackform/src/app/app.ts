import { Component } from '@angular/core';
import { FeedbackForm } from './feedbackform/feedbackform';  

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FeedbackForm],
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
})
export class App {}
