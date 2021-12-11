let brandHtmlElement = document.querySelector('#brandId');
brandHtmlElement.addEventListener('change', onChangeHandler);

let baseURL = 'http://localhost:8080/api/model';
// let baseURL = "https://gentle-temple-45146.herokuapp.com/api/model";
async function onChangeHandler(e){
    let brandName = e.target.value;
    let response = await fetch(`${baseURL}/${brandName}`);
    let data = await response.json();
    console.log(data);
    let modelHtmlSelectElement = document.querySelector('#modelId');
    createOptionElement(modelHtmlSelectElement,data);
}


function createOptionElement(selectElement, data){
    let children=Array.from(selectElement.children);
    children.forEach(child=>selectElement.removeChild(child));
    let defOption=document.createElement('option')
    defOption.value='-Select a model-';
    defOption.text=' - Select a model - '
    defOption.selected=true;

    selectElement.append(defOption);

    for (const model of data) {
        let tempOption=document.createElement('option');
        tempOption.value=model.name;
        tempOption.text=model.name;
        selectElement.append(tempOption);
    }

}
