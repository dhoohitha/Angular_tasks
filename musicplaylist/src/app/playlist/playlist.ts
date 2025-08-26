import { Component, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

type Song = {
  id: number;
  songName: string;
  artist: string;
  album: string;
  duration: string; // mm:ss
  rating: number;   // 1–5
};

@Component({
  selector: 'app-playlist',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './playlist.html',
  styleUrls: ['./playlist.css'],
})
export class Playlist {
    private seed: Song[] = [
    { id: 1,  songName: 'Samajavaragamana',  artist: 'Sid Sriram',          album: 'Ala Vaikunthapurramuloo', duration: '03:49', rating: 4.8 },
    { id: 2,  songName: 'Butta Bomma',       artist: 'Armaan Malik',        album: 'Ala Vaikunthapurramuloo', duration: '03:17', rating: 4.5 },
    { id: 3,  songName: 'Srivalli',          artist: 'Sid Sriram',          album: 'Pushpa: The Rise',        duration: '03:41', rating: 4.7 },
    { id: 4,  songName: 'Naatu Naatu',       artist: 'Rahul Sipligunj, Kaala Bhairava', album: 'RRR',       duration: '03:35', rating: 4.9 },
    { id: 5,  songName: 'Ramuloo Ramulaa',   artist: 'Anurag Kulkarni, Mangli', album: 'Ala Vaikunthapurramuloo', duration: '04:07', rating: 4.6 },
    { id: 6,  songName: 'Inkem Inkem Inkem Kaavaale', artist: 'Sid Sriram', album: 'Geetha Govindam',        duration: '03:44', rating: 4.7 },
    { id: 7,  songName: 'Devaraase Devara',  artist: 'Kaala Bhairava',      album: 'Baahubali 2',             duration: '04:10', rating: 4.4 },
    { id: 8,  songName: 'Yenti Yenti',       artist: 'Chinmayi Sripaada',   album: 'Geetha Govindam',         duration: '03:38', rating: 4.3 },
    { id: 9,  songName: 'Sittharala Sirapadu', artist: 'S. Thaman, Rahul Sipligunj', album: 'Ala Vaikunthapurramuloo', duration: '04:06', rating: 4.2 },
    { id:10,  songName: 'Dosti',             artist: 'Vedala Hemachandra',  album: 'RRR',                     duration: '04:25', rating: 4.6 },
    { id:11,  songName: 'Chitti',            artist: 'Ram Miriyala',        album: 'Jathi Ratnalu',           duration: '03:19', rating: 4.4 },
    { id:12,  songName: 'Ee Raathale',       artist: 'Yuvan Shankar Raja, Harika Narayan', album: 'Radhe Shyam', duration: '03:34', rating: 4.3 },
    { id:13,  songName: 'Oke Oka Jeevitham', artist: 'Armaan Malik',        album: 'Mr. Majnu',               duration: '03:57', rating: 4.2 },
    { id:14,  songName: 'Nee Kannu Neeli Samudram', artist: 'Javed Ali',    album: 'Uppena',                  duration: '03:55', rating: 4.6 },
    { id:15,  songName: 'Srimanthuda',       artist: 'Shankar Mahadevan',   album: 'Srimanthudu',             duration: '03:28', rating: 4.1 },
  ];


  songs = signal<Song[]>(this.seed);

  artistFilter = signal<string>(''); 
  albumFilter  = signal<string>(''); 
  searchText   = signal<string>(''); 

  artists = computed(() =>
    Array.from(new Set(this.songs().map(s => s.artist))).sort()
  );
  albums = computed(() =>
    Array.from(new Set(this.songs().map(s => s.album))).sort()
  );

  readonly TOP_THRESHOLD = 4.5;

  filtered = computed(() => {
    const byArtist = this.artistFilter();
    const byAlbum  = this.albumFilter();
    const q        = this.searchText().trim().toLowerCase();

    return this.songs().filter(s => {
      const okArtist = !byArtist || s.artist === byArtist;
      const okAlbum  = !byAlbum  || s.album  === byAlbum;
      const okQuery  = !q || s.songName.toLowerCase().includes(q);
      return okArtist && okAlbum && okQuery;
    });
  });

  isTopRated(song: Song) {
    return song.rating >= this.TOP_THRESHOLD;
  }

  clearFilters() {
    this.artistFilter.set('');
    this.albumFilter.set('');
    this.searchText.set('');
  }

  play(song: Song) {

    console.log('Play clicked:', song.songName);
  }
}
