import { Component, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';

type Movie = {
  id: number;
  title: string;
  poster: string;     
  genre: string;
  rating: number;     
  description: string;
};

@Component({
  selector: 'app-movies',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule, MatFormFieldModule, MatSelectModule],
  templateUrl: './movies.html',
  styleUrls: ['./movies.css'],
})
export class Movies {
 
  private poster(text: string) {
    const t = encodeURIComponent(text.replace(/\s+/g, '+'));
    return `https://via.placeholder.com/300x450?text=${t}`;
  }

readonly movies = signal<Movie[]>([
  {
    id: 1,
    title: 'Ala Vaikunthapurramuloo',
    poster: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCso3GZvWX4lM6HF_mlux0UrjezuddNlISCA&s',
    genre: 'Family/Drama',
    rating: 4.7,
    description: 'A light-hearted family drama packed with music and warmth.'
  },
  {
    id: 2,
    title: 'Baahubali: The Beginning',
    poster: 'https://m.media-amazon.com/images/M/MV5BM2YxZThhZmEtYzM0Yi00OWYxLWI4NGYtM2Y2ZDNmOGE0ZWQzXkEyXkFqcGc@._V1_.jpg',
    genre: 'Action/Epic',
    rating: 4.9,
    description: 'Epic saga about destiny, power, and grandeur.'
  },
  {
    id: 3,
    title: 'RRR',
    poster: 'https://image.tmdb.org/t/p/original/nEufeZlyAOLqO2brrs0yeF1lgXO.jpg',
    genre: 'Action/Drama',
    rating: 4.8,
    description: 'Two revolutionaries forge a legendary friendship.'
  },
  {
    id: 4,
    title: 'Jersey',
    poster: 'https://edgroom-blogs.s3.ap-south-1.amazonaws.com/202310082003498655315_61fdac34a3c1ec89cdd31a3c0347b7a8.jpg',
    genre: 'Sports/Drama',
    rating: 4.4,
    description: 'A former cricketer chases his dream against all odds.'
  },
  {
    id: 5,
    title: 'Agent Sai Srinivasa Athreya',
    poster: 'https://m.media-amazon.com/images/M/MV5BOTljNDVhYTUtMzMyNi00NzEzLTgxYjQtODVlZDc1MGU3ZmMyXkEyXkFqcGc@._V1_.jpg',
    genre: 'Mystery/Comedy',
    rating: 4.3,
    description: 'A quirky detective gets tangled in a bigger case.'
  },
  {
    id: 6,
    title: 'Pushpa: The Rise',
    poster: 'https://assets-in.bmscdn.com/iedb/movies/images/mobile/thumbnail/xlarge/pushpa--the-rise-et00129538-08-12-2021-01-21-46.jpg',
    genre: 'Action',
    rating: 4.5,
    description: 'A smuggler rises through grit and swagger.'
  },
]);


  readonly TOP = 4.6;
  readonly stars = [1, 2, 3, 4, 5];
  round(n: number) { return Math.round(n); }

  genreFilter = signal<string>('');

  genres = computed(() => {
    const set = new Set(this.movies().map(m => m.genre));
    return Array.from(set).sort();
  });

  filtered = computed(() => {
    const g = this.genreFilter();
    const list = this.movies();
    return g ? list.filter(m => m.genre === g) : list;
  });

  avgRating = computed(() => {
    const list = this.movies();
    if (!list.length) return 0;
    return list.reduce((s, m) => s + m.rating, 0) / list.length;
  });

  isTop(movie: Movie) {
    return movie.rating >= this.TOP;
  }
}
