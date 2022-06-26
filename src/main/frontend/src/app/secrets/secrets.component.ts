import { SecretService } from './../services/secret.service';
import { Secrets } from './../dto/Secrets';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-secrets',
  templateUrl: './secrets.component.html',
  styleUrls: ['./secrets.component.sass']
})
export class SecretsComponent implements OnInit {

  secrets !: Secrets[];
  loading = false;

  rows = [
    { name: 'Austin', gender: 'Male', company: 'Swimlane' },
    { name: 'Dany', gender: 'Male', company: 'KFC' },
    { name: 'Molly', gender: 'Female', company: 'Burger King' }
  ];
  columns = [{ prop: 'name' }, { name: 'Gender' }, { name: 'Company' }];

  constructor(private secretsService: SecretService) { }

  ngOnInit(): void {
    this.secretsService.listSecrets()
      .then((secrets) => {
        this.secrets = secrets;
        this.loading = false;
      }).catch(err => {
        console.log(err);
        this.loading = false;
      });;
  }

}
