import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-header',
  templateUrl: './user-header.component.html',
  styleUrls: ['./user-header.component.scss']
})
export class UserHeaderComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  editProfile() {
    this.router.navigateByUrl('/editUserProfile');
  }

  logout() {
    this.router.navigateByUrl('/Login');
  }
}
