import { AbstractControl, FormGroup } from "@angular/forms";
export function ConfirmPasswordValidator( controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      let control = formGroup.controls[controlName];
      let matchingControl = formGroup.controls[matchingControlName]
      if (control.value !== matchingControl.value) {
        return matchingControl.setErrors({ confirmPasswordValidator: true });
      } else {
        return matchingControl.setErrors(null);
      }
    };
  }