import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PolicyUsersComponent } from './list/policy-users.component';
import { PolicyUsersDetailComponent } from './detail/policy-users-detail.component';
import { PolicyUsersUpdateComponent } from './update/policy-users-update.component';
import { PolicyUsersDeleteDialogComponent } from './delete/policy-users-delete-dialog.component';
import { PolicyUsersRoutingModule } from './route/policy-users-routing.module';

@NgModule({
  imports: [SharedModule, PolicyUsersRoutingModule],
  declarations: [PolicyUsersComponent, PolicyUsersDetailComponent, PolicyUsersUpdateComponent, PolicyUsersDeleteDialogComponent],
  entryComponents: [PolicyUsersDeleteDialogComponent],
})
export class PolicyUsersModule {}
