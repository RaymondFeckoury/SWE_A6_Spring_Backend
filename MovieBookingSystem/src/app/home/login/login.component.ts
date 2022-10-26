import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialog, MAT_DIALOG_DATA  } from '@angular/material/dialog';
import { bmsApiService } from 'src/app/services/bmsapi.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  emailError:boolean = false;
  passwordFieldError:boolean = false;
  constructor(private fb: FormBuilder,private dialogRef: MatDialog,private router: Router,private _bmsAs:bmsApiService) { }


  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email : ['',[Validators.required, Validators.email]],
      password : ['', [Validators.required]]
    });
  }

  validityCheck() {
    
    if(this.loginForm.value.email == "" || this.loginForm.value.password == "") {
      if(this.loginForm.value.email == "") {
        this.emailError = true;
      } else {
        this.emailError = false;
      } 
      if (this.loginForm.value.password == "" ){
        this.passwordFieldError = true; 
      }else {
        this.passwordFieldError = false;
      }
      return false;
    }
    return true;
  }

  login() {
    var data =  {
      "email": "email123@email.com",
      "password": "nicepassword"
    };
    let data2 = JSON.stringify(data);
    // login endpoint returns boolean
    this._bmsAs.putdata(data2).subscribe(res=>{
      // Send the user to the home page if valid credentials are entered
      if (res) this.router.navigateByUrl('/userHomePage');
      console.log(res," put method result");
    });
    //Api to check valid User 
    if(this.validityCheck()) {
      // this.router.navigateByUrl('/userHomePage');
      console.log("valid form");
    } else {
      console.log("invalid form");
    }
    console.log(this.loginForm.errors ," error login form");
    
  }

}
