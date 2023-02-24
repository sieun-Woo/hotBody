const rowsPerPage = 10;
const rows = document.querySelectorAll('#article-list #tbody div');
const rowsCount = rows.length;
const pageCount = Math.ceil(rowsCount / rowsPerPage);
const numbers = document.querySelector('#pagination');


// 페이지네이션 생성
/*
대상.innerHTML = <li><a href="">1</a></li>
for(초기값;조건;증간){

}
*/
for(let i = 1; i <= pageCount.length; i++){
    numbers.innerHTML += `<li><a href="">${i}</a></li>`;
}
