import { Component, signal, computed, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

type Filter = 'all' | 'active' | 'completed';
type Task = { id: number; title: string; done: boolean };

const STORAGE_KEY = 'ng18-todos';

@Component({
  selector: 'app-todo',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './todo.html',
  styleUrls: ['./todo.css'],
})
export class Todo {
  // state
  todos = signal<Task[]>(this.load());
  filter = signal<Filter>('all');
  newTitle = signal('');

  // derived
  remaining = computed(() => this.todos().filter(t => !t.done).length);
  any = computed(() => this.todos().length > 0);
  allDone = computed(() => this.any() && this.remaining() === 0);
  visible = computed(() => {
    const f = this.filter();
    const list = this.todos();
    if (f === 'active') return list.filter(t => !t.done);
    if (f === 'completed') return list.filter(t => t.done);
    return list;
  });

  // persist on change
  persist = effect(() => {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(this.todos()));
  });

  private load(): Task[] {
    try {
      const raw = localStorage.getItem(STORAGE_KEY);
      return raw ? (JSON.parse(raw) as Task[]) : [];
    } catch {
      return [];
    }
  }

  add() {
    const title = this.newTitle().trim();
    if (!title) return;
    const task: Task = { id: Date.now(), title, done: false };
    this.todos.update(list => [task, ...list]);
    this.newTitle.set('');
  }

  toggle(id: number) {
    this.todos.update(list =>
      list.map(t => (t.id === id ? { ...t, done: !t.done } : t))
    );
  }

  toggleAll(val: boolean) {
    this.todos.update(list => list.map(t => ({ ...t, done: val })));
  }

  remove(id: number) {
    this.todos.update(list => list.filter(t => t.id !== id));
  }

  editTitle(task: Task, newTitle: string) {
    const title = newTitle.trim();
    if (!title) { this.remove(task.id); return; }
    this.todos.update(list =>
      list.map(t => (t.id === task.id ? { ...t, title } : t))
    );
  }

  clearCompleted() {
    this.todos.update(list => list.filter(t => !t.done));
  }

  setFilter(f: Filter) {
    this.filter.set(f);
  }
}
