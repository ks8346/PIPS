import { Proposal } from './proposal';

describe('Proposal', () => {
  it('should create an instance', () => {
    expect(new Proposal("ks8346","This is title","this is desc",[{id:1,name:"PS"},{id:2,name:"R&D"}])).toBeTruthy();
  });
});
