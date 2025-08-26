import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-feedbackform',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './feedbackform.html',
  styleUrls: ['./feedbackform.css'],
})
export class FeedbackForm {
  submitted = signal(false);
  form: FormGroup; 

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      subject: ['', [Validators.required, Validators.maxLength(40)]],
      comments: ['', [Validators.required, Validators.maxLength(350)]],
    });
  }

  isInvalid(ctrl: string): boolean {
    const c = this.form.get(ctrl);
    return !!c && c.invalid && (c.touched || c.dirty || this.submitted());
  }

  get subjectLen()  { return (this.form.get('subject')?.value as string || '').length; }
  get commentsLen() { return (this.form.get('comments')?.value as string || '').length; }

  onSubmit() {
    this.submitted.set(true);
    if (this.form.invalid) {
      const first = Object.keys(this.form.controls).find(k => this.form.get(k)?.invalid);
      if (first) (document.querySelector(`[formControlName="${first}"]`) as HTMLElement | null)?.focus();
      return;
    }
    this.form.reset();
  }

  newResponse() {
    this.submitted.set(false);
    this.form.reset();
  }
}
