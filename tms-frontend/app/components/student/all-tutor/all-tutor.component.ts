import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Tutor } from 'src/app/interfaces/tutor.model';
import { StudentService } from 'src/app/services/student.service';

@Component({
  selector: 'app-all-tutor',
  templateUrl: './all-tutor.component.html',
  styleUrls: ['./all-tutor.component.scss'],
})
export class AllTutorComponent implements OnInit {
  constructor(
    private studentService: StudentService,
    private _router: Router
  ) {}

  ngOnInit(): void {
    this.studentService.getAllTutors().subscribe((resp) => {
      this.otherTutor = resp.otherTutors;
    });
  }

  otherTutor: Tutor[] = Array();
  subscribeTutor(id: number) {
    this.studentService.subscribeTutor(id).subscribe((resp) => {
      window.location.reload();
    });
  }
}
