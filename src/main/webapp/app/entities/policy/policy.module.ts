import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PolicyComponent } from './list/policy.component';
import { PolicyDetailComponent } from './detail/policy-detail.component';
import { PolicyUpdateComponent } from './update/policy-update.component';
import { PolicyDeleteDialogComponent } from './delete/policy-delete-dialog.component';
import { PolicyRoutingModule } from './route/policy-routing.module';

@NgModule({
  imports: [SharedModule, PolicyRoutingModule],
  declarations: [PolicyComponent, PolicyDetailComponent, PolicyUpdateComponent, PolicyDeleteDialogComponent],
  entryComponents: [PolicyDeleteDialogComponent],
})
export class PolicyModule {}
