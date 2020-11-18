import { AbstractControl, FormGroup } from "@angular/forms";

/**This method validates if inputs of password and confirm password fields matches  */
export function ConfirmPasswordValidator( controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      /**Stores input entered in password field */
      let control = formGroup.controls[controlName];

      /**Stores input entered in confirm password field */
      let matchingControl = formGroup.controls[matchingControlName]
      if (control.value !== matchingControl.value) {
        return matchingControl.setErrors({ confirmPasswordValidator: true });
      } else {
        return matchingControl.setErrors(null);
      }
    };
  }