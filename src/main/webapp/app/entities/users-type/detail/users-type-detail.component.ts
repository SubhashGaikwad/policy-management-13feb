import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUsersType } from '../users-type.model';

@Component({
  selector: 'jhi-users-type-detail',
  templateUrl: './users-type-detail.component.html',
})
export class UsersTypeDetailComponent implements OnInit {
  usersType: IUsersType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usersType }) => {
      this.usersType = usersType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
