import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPolicyUsers } from '../policy-users.model';

@Component({
  selector: 'jhi-policy-users-detail',
  templateUrl: './policy-users-detail.component.html',
})
export class PolicyUsersDetailComponent implements OnInit {
  policyUsers: IPolicyUsers | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ policyUsers }) => {
      this.policyUsers = policyUsers;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
