class Pulse {
	constructor(divPulse, divContainer) {
		this.divPulse = divPulse;
		this.divContainer = divContainer;
	}
	Show() {

 		var rectC = divContainer.getBoundingClientRect();
 		var rectP = divPulse.getBoundingClientRect();
 		divPulse.style.left = rectC.width / 2 - rectP.width / 2;
 		divPulse.style.top = rectC.height / 2 - rectP.height / 2;
		divPulse.style.opacity=0.5;
	}

	Hide() {
		divPulse.style.opacity=0;
	}
}