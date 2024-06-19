    function displayResult(title, result) {
        document.getElementById('resultTitle').innerText = title;
        document.getElementById('resultBody').textContent = JSON.stringify(result, null, 2);
    }

    const apiUrl = "http://localhost:8080";
    const bookUrl = apiUrl+"/api/book";
    const authorUrl = apiUrl+"/api/author";
    const publisherUrl = apiUrl+"/api/publisher";

    // BOOK

    const createBook = async () => {

    const authors = document.getElementById('authorNames').value;
    const authorNames = authors.includes(',') ?
        authors.split(',').map(el => el.toLowerCase().trim()) :
        [authors.toLowerCase().trim()];

        const book = {
            title: document.getElementById('bookTitle').value.toLowerCase().trim(),
            publisherName: document.getElementById('publisherName').value.toLowerCase().trim(),
            authorNames,
            reviewComment: document.getElementById('reviewComment').value
        };

        try {
            const response = await fetch(bookUrl, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(book)
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const data = await response.json();
            displayResult('Book - Create', data);
        } catch (error) {
            displayResult('Book - Create', { error: error.message });
        }
    }

    const findAllBooks = async () => {
        try {
            const response = await fetch(bookUrl, {
                method: "GET",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const result = await response.json();
            displayResult('Book - Find All', result);
        } catch (error) {
            displayResult('Book - Find All', { error: error.message });
        }
    }

    const findAllBooksPagination = async () => {
        try {
            const pageAndSize = document.getElementById('findAllBooksPaginationButton').value.split(',');
            const page = pageAndSize[0];
            const size = pageAndSize[1];

            const response = await fetch(bookUrl+"?page="+page+"&size="+size, {
                method: "GET",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const result = await response.json();
            displayResult('Book - Find All Pagination page: ' + page + 'size: ' + size, result);
        } catch (error) {
            displayResult('Book - Find All Pagination', { error: error.message });
        }
    }

    const findAllBooksByAuthorName = async () => {
        const authorName = document.getElementById('findAllBooksByAuthorNameButton').value.toLowerCase().trim();

        try {
            const response = await fetch(bookUrl+"/q_author="+authorName, {
                method: "GET",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const result = await response.json();
            displayResult('Book - Find all by author name: ' + authorName, result);
        } catch (error) {
            displayResult('Book - Find all by author name: ' + authorName, { error: error.message });
        }
    }

    const findAllBooksByPublisherName = async () => {
        const publisherName = document.getElementById('findAllBooksByPublisherNameButton').value.toLowerCase().trim();

        try {
            const response = await fetch(bookUrl+"/q_publisher="+publisherName, {
                method: "GET",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const result = await response.json();
            displayResult('Book - Find all by publisher: ' + publisherName, result);
        } catch (error) {
            displayResult('Book - Find all by publisher: ' + publisherName, { error: error.message });
        }
    }

    const findBookByName = async () => {
        const bookName = document.getElementById('findBookByNameButton').value.toLowerCase().trim();

        try {
            const response = await fetch(bookUrl+"/q_name="+bookName, {
                method: "GET",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const result = await response.json();
            displayResult('Book - Find by Book name: ' + bookName, result);
        } catch (error) {
            displayResult('Book - Find by Book name: ' + bookName, { error: error.message });
        }
    }

    const findBookById = async () => {
        const id = document.getElementById('findBookByIdButton').value;

        try {
            const response = await fetch(bookUrl+"/"+id, {
                method: "GET",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const result = await response.json();
            displayResult('Book - Find book by id: ' + id, result);
        } catch (error) {
            displayResult('Book - Find book by id: ' + id, { error: error.message });
        }
    }

    const deleteBook = async () => {
        const id = document.getElementById('deleteBookId').value;

        try {
            const response = await fetch(bookUrl+"/"+id, {
                method: "DELETE",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const result = await response.json();
            displayResult('Book - Delete', { id, status: 'Deleted: ' + result });
        } catch (error) {
            displayResult('Book - Delete: ' + id, { error: error.message });
        }
    }

    // AUTHOR

    const createAuthor = async () => {
        const author = { name: document.getElementById('authorName').value.toLowerCase().trim() };

        try {
            const response = await fetch(authorUrl, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(author)
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const data = await response.json();
            displayResult('Author - Create', data);
        } catch (error) {
            displayResult('Author - Create', { error: error.message });
        }
    }

    const findAllAuthors = async () => {
        try {
            const response = await fetch(authorUrl, {
                method: "GET",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const result = await response.json();
            displayResult('Author - Find All', result);
        } catch (error) {
            displayResult('Author - Find All', { error: error.message });
        }
    }

    const findAuthorByName = async () => {
        const name = document.getElementById('findAuthorByNameButton').value.toLowerCase().trim();

        try {
            const response = await fetch(authorUrl+"/q_name="+name, {
                method: "GET",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const result = await response.json();
            displayResult('Author - Find By name: ' + name, result);
        } catch (error) {
            displayResult('Author - Find By name: ' + name, { error: error.message });
        }
    }

    const findAuthorById = async () => {
        const id = document.getElementById('findAuthorByIdButton').value;

        try {
            const response = await fetch(authorUrl+"/"+id, {
                method: "GET",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const result = await response.json();
            displayResult('Author - Find By Id: ' + id, result);
        } catch (error) {
            displayResult('Author - Find By Id: ' + id, { error: error.message });
        }
    }

    const addBookToAuthor = async () => {
        const ids = document.getElementById('addBookToAuthorButton').value.split(',');
        const idAuthor = ids[0];
        const idBook = ids[1];

        try {
            const response = await fetch(authorUrl+"/"+idAuthor+"/"+idBook, {
                method: "PUT",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const result = await response.json();
            displayResult('Author updated: ' + idAuthor, result);
        } catch (error) {
            displayResult('Author updated: ' + idAuthor, { error: error.message });
        }
    }

    const deleteAuthor = async () => {
        const id = document.getElementById('deleteAuthorId').value;

        try {
            const response = await fetch(authorUrl+"/"+id, {
                method: "DELETE",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const result = await response.json();
            displayResult('Author - Delete', { id, status: 'Deleted: ' + result });
        } catch (error) {
            displayResult('Author - Delete: ' + id, { error: error.message });
        }
    }

    // PUBLISHER

    const createPublisher = async () => {
        const publisher = { name: document.getElementById('publisherNameForm').value.toLowerCase().trim() };

        try {
            const response = await fetch(publisherUrl, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(publisher)
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const data = await response.json();
            displayResult('Publisher - Create', data);
        } catch (error) {
            displayResult('Publisher - Create', { error: error.message });
        }
    }

    const findAllPublishers = async () => {
        try {
            const response = await fetch(publisherUrl, {
                method: "GET",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const result = await response.json();
            displayResult('publisherUrl - Find All', result);
        } catch (error) {
            displayResult('publisherUrl - Find All', { error: error.message });
        }
    }

    const findPublisherByName = async () => {
        const name = document.getElementById('findPublisherByNameButton').value.toLowerCase().trim();

        try {
            const response = await fetch(publisherUrl+"/q_name="+name, {
                method: "GET",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const result = await response.json();
            displayResult('Publisher - Find By Name: ' + name, result);
        } catch (error) {
            displayResult('Publisher - Find By Name: ' + name, { error: error.message });
        }
    }

    const findPublisherById = async () => {
        const id = document.getElementById('findPublisherByIdButton').value;

        try {
            const response = await fetch(publisherUrl+"/"+id, {
                method: "GET",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const result = await response.json();
            displayResult('Publisher - Find By ID: ' + id, result);
        } catch (error) {
            displayResult('Publisher - Find By ID: ' + id, { error: error.message });
        }
    }

    const deletePublisher = async () => {
        const id = document.getElementById('deletePublisherId').value;

        try {
            const response = await fetch(publisherUrl+"/"+id, {
                method: "DELETE",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                let result = await decodeBody(response);
                throw new Error(`HTTP Status: ${response.status} - ${result}`);
            }

            const result = await response.json();
            displayResult('Publisher - Delete', { id, status: 'Deleted: ' + result });
        } catch (error) {
            displayResult('Publisher - Delete', { error: error.message });
        }
    }

    const scrollToTop = () => {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    const decodeBody = async (response) => {
        const reader = response.body.getReader();
        let result = '';
        let decoder = new TextDecoder('utf-8');

        while (true) {
            const { done, value } = await reader.read();
            if (done) break;
            result += decoder.decode(value, { stream: true });
            }

        result += decoder.decode();
        return result;
    }