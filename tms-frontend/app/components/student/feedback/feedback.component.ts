import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { StudentService } from 'src/app/services/student.service';
import { StarRatingColor } from '../star-rating/star-rating.component';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.scss'],
})
export class FeedbackComponent implements OnInit {
  constructor(public _studentService: StudentService, private router: Router) {}

  rating: number = 0;
  starCount: number = 5;
  starColor: StarRatingColor = StarRatingColor.accent;
  starColorP: StarRatingColor = StarRatingColor.primary;
  starColorW: StarRatingColor = StarRatingColor.warn;
  ngOnInit(): void {}
  onRatingChanged(rating: number) {
    this.rating = rating;
  }
  onSubmitFeedback(form: NgForm) {
    if (form.invalid) {
      return;
    }
    console.log(this.rating);

    const feedback = {
      feedbackData: form.value.feedback,
      rating: this.rating,
    };
    this._studentService.createFeedback(
      feedback,
      this._studentService.tutor.id
    );
  }
}
