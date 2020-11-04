import { Proposal } from './proposal';

describe('Proposal', () => {
  it('should create an instance', () => {
    expect(new Proposal("ks8346","Hey how are you",["PS","R&D"])).toBeTruthy();
  });
});
