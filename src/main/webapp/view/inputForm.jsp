<form name="bookForm" method="post" action="/books/update">
<input type="hidden" name="id" value='${book.id}' />
    Author: <input type="text" name="author" value="<c:out value='${book.author}' />"/> <br/>
    Name: <input type="text" name="name"/ value="<c:out value='${book.name}' />"> <br/>
    Pages: <input type="number" name="countPages" value="<c:out value='${book.countPages}' />"/> <br/>
    Publication Year: <input type="number" min="1900" max="2099" step="1" value="<c:out value='${book.publicationYear}' />" name="publicationYear"/> <br/>
    Description: <input type="text" name="description" "<c:out value='${book.description}' />"/> <br/>
    Genre: <select id="genre" name="genre" selected="<c:out value='${book.genre}' />">
               <option value="ROMANCE">ROMANCE</option>
               <option value="DETECTIVE">DETECTIVE</option>
               <option value="FANTASY">FANTASY</option>
               <option value="HISTORICAL">HISTORICAL</option>
               <option value="HORROR">HORROR</option>
               <option value="WOMEN_FICTION">WOMEN_FICTION</option>
             </select>
    <input type="submit" value="Update Book" />
</form>