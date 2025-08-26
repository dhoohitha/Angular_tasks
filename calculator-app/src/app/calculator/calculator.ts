import { Component } from '@angular/core';

@Component({
  selector: 'app-calculator',
  standalone: true,
  templateUrl: './calculator.html',
  styleUrls: ['./calculator.css'],
})
export class Calculator {
  currentInput = '';
  result = '0';

  append(v: string) { this.currentInput += v; }
  clear() { this.currentInput = ''; this.result = '0'; }
  backspace() { this.currentInput = this.currentInput.slice(0, -1); }

  calculate() {
    try {
      // demo only; avoid eval in production
      // eslint-disable-next-line no-eval
      this.result = eval(this.currentInput).toString();
    } catch {
      this.result = 'Error';
    }
  }
}
