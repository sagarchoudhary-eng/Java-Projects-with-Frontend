import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'subTutorSearch',
})
export class SubTutorSearchPipe implements PipeTransform {
  transform(tutors: any[], args: string): any[] {
    if (!tutors) return [];
    if (!args) return tutors;
    args = args.toLowerCase();
    return tutors.filter((tutor) => {
      return (
        tutor.tutor.firstName.toLowerCase().includes(args) ||
        tutor.tutor.extraDetails.technology.toLowerCase().includes(args)
      );
    });
  }
}
