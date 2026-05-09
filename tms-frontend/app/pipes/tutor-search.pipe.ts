import { Pipe, PipeTransform } from '@angular/core';
import { SearchTutorComponent } from '../components/student/search-tutor/search-tutor.component';
import { StudentService } from '../services/student.service';

@Pipe({
  name: 'tutorSearch',
})
export class TutorSearchPipe implements PipeTransform {
  transform(tutors: any[], args: string): any[] {
    if (!tutors) return [];
    if (!args) return tutors;
    args = args.toLowerCase();
    return tutors.filter((tutor) => {
      if (
        tutor.firstName.toLowerCase().includes(args) ||
        tutor.extraDetails.technology.toLowerCase().includes(args)
      ) {
        SearchTutorComponent.flag(false);
        return (
          tutor.firstName.toLowerCase().includes(args) ||
          tutor.extraDetails.technology.toLowerCase().includes(args)
        );
      } else SearchTutorComponent.flag(true);
    });
  }
}
