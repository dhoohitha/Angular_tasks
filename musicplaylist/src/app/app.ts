import { Component } from '@angular/core';
import { Playlist } from './playlist/playlist';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [Playlist],
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
})
export class App {}
