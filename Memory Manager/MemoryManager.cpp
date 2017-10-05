/*************************************************************************************
* Author: Frankie Fasola
* Date: 3/10/17
* Implementation of a memory mangement system using only 16K of memory. 16K is 65536,
* which can be stored in two bytes. This means every allocation will need an additional 
* two bytes in the array of memory to represent the size. This overhead reduces the total
* memory available, but allows allocation and deallocation to receive some advantages like
* jumping over memory that is allocated.
*************************************************************************************/

#include "MemoryManager.h"

namespace MemoryManager
{
  const int MM_POOL_SIZE = 65536;
  char MM_pool[MM_POOL_SIZE];

  int size(int, int);

  /*************************************************************************************
  * Author: Frankie Fasola
  * Date: 3/10/2017, updated 3/12/17
  * Initilizes the memory manager by setting all of the available memory to NULL.
  *************************************************************************************/
  void initializeMemoryManager(void)
  {
	  // Check rest of memory is set to NULL
	  for (int i = 0; i < MM_POOL_SIZE; i++)
		  MM_pool[i] = '\0';
  }

  /**************************************************************************************
  * Author: Frankie Fasola
  * Date: 3/11/17
  * Parameter: aSize - The amount of memory to allocate.
  * Return: Memory from the pool that is allocated for the request.
  *
  * Allocates memory from the memory pool. If not enough memory is available or
  * the requested size is too large onOutOfMemory() is called, exiting the program.
  * The actual memory used will be aSize + 2 bytes because two bytes are used to store 
  * the size of the requested memory.
  *************************************************************************************/
  void* allocate(int aSize)
  {
	  if (aSize >= MM_POOL_SIZE)
	  {
		  onIllegalOperation("Memory allocation size is greater than memory pool size; request is too large!");
		  return nullptr;
	  }
	  else
	  {
		  int begin = 0;
		  int end = 0;
		  for (int i = 0; i < MM_POOL_SIZE; i++)
		  {
			  if (MM_pool[i] == '\0')
			  {
				  if (end == 0)
					  begin = i;
				  end++;
			  }
			  else
			  {
				  end = 0;
				  //Get the integer value of the two size bytes
				  int byte1 = (int)MM_pool[i];
				  int byte2 = (int)MM_pool[i + 1];
				  //Jump from current index to end of allocation then continue
				  i += 1 + size(byte2, byte1);
			  }
			  if (end >= aSize + 2)
				  break;
		  }
		  if (end < aSize + 2)
			  onOutOfMemory();
		  else
		  {
			  // Start two blocks AFTER the position to use the first two bytes to store size of allocation
			  for (int i = begin + 2; i < begin + 2 + aSize; i++)
				  MM_pool[i] = 'A';
			  MM_pool[begin] = (char)((int)((aSize + 1) / 256) + 1);
			  MM_pool[begin + 1] = (char)((aSize - ((int)MM_pool[begin] - 1) * 256) + 1);
			  return ((void*)(MM_pool + begin + 2));
		}
	  }
  }

  /**************************************************************************************
  * Author: Frankie Fasola
  * Date: 3/12/17
  * Parameter: aPointer - The memory to deallocate.
  * Deallocates memory, returning it back to the pool.
  *************************************************************************************/
  void deallocate(void* aPointer)
  {
	  /* 
	   * First check if the pointer is NULL
	   * If the pointer is not NULL then find the size of the pointer using the 2 bytes
	   * before it. Change the memory to NULL and the two bytes.
	   */
	  if (aPointer == nullptr)
		  onIllegalOperation("ERROR: NULL address passed to deallocate.");
	  else
	  {
		  // The two blocks before the pointer contain the allocation size information.
		  int byte1 = (int)(*((char*)aPointer - 1));
		  int byte2 = ((int)(*((char*)aPointer - 2)));
		  int _size = size(byte1, byte2);
		  for (int i = 0; i < _size; i++)
			  *((char*)aPointer + i) = '\0';
		  *((char*)aPointer - 1) = '\0';
		  *((char*)aPointer - 2) = '\0';
	  }
	  
  }

  /**************************************************************************************
  * Author: Frankie Fasola
  * Date: 3/12/17
  * Return: The amount of available memory.
  *
  * Scans the memory pool and tracks the amount of available memory.
  *************************************************************************************/
  int freeRemaining(void)
  {
	  int remaining = 0;
	  for (int i = 0; i < MM_POOL_SIZE; i++)
	  {
		  if (MM_pool[i] == '\0')
			  remaining++;
		  else
		  {
			  // Use the two bytes to jump over the allocated memory.
			  int byte1 = (int)MM_pool[i];
			  int byte2 = (int)MM_pool[i + 1];
			  i += 1 + size(byte2, byte1);
		  }
	  }
	  return remaining;
  }

  /**************************************************************************************
  * Author: Frankie Fasola
  * Date: 3/11/17
  * Return: The largest block of free memory available in the pool.
  *
  * Scans the memory pool and tracks the largest amount of available free memory.
  *************************************************************************************/
  int largestFree(void)
  {
	  /*
	  * Go through the pool and check if the position in the array is NULL;
	  * if the position is NULL start counting the amount of NULL blocks in a row.
	  * Keep track of the largest block.
	  */
	  int largest = 0;
	  int currentSize = 0;
	  for (int i = 0; i < MM_POOL_SIZE; i++)
	  {
		  if (MM_pool[i] == '\0')
		  {
			  currentSize++;
			  if (currentSize > largest)
				  largest = currentSize;
		  }
		  else 
		  {
			  // Use the two bytes before the allocation to detertmine size and jump over it
			  currentSize = 0;
			  int byte1 = (int)MM_pool[i];
			  int byte2 = (int)MM_pool[i + 1];
			  i += 1 + size(byte2, byte1);
		  }			 
	  }
    return largest;
  }

  /**************************************************************************************
  * Author: Frankie Fasola
  * Date: 3/11/17
  * Return: The smallest block of free memory available in the pool.
  *
  * Scans the memory pool and tracks the smallest amount of available free memory.
  *************************************************************************************/
  int smallestFree(void)
  {
	  /*
	  * Go through the pool and check if the position in the array is NULL;
	  * if the position is NULL start counting the amount of NULL blocks in a row.
	  * Keep track of the smallest block.
	  */
	  int smallest = 0;
	  int currentSize = 0;
	  for (int i = 0; i < MM_POOL_SIZE; i++)
	  {
		  if (MM_pool[i] == '\0')
		  {
			  currentSize++;
			  if (currentSize < smallest)
				  smallest = currentSize;
		  }
		  else
		  {
			  // Use the two bytes before the allocation to detertmine size and jump over it
			  currentSize = 0;
			  int byte1 = (int)MM_pool[i];
			  int byte2 = (int)MM_pool[i + 1];
			  i += 1 + size(byte2, byte1);
		  }
	  }
	  return smallest;
  }

  /**************************************************************************************
  * Author: Frankie Fasola
  * Date: 3/11/17
  * Parameter: a - The first byte of the allocated memory.
  * Parameter: b - The second byte of the allocated memory.
  *
  * Determines the size of allocated memory by adding the two bytes and multiplying them
  * by 256 because 256 is the biggest value that can be stored in a single byte. 
  * The values are not unsigned so there is a possibility they could be negative and 
  * the check is used to make sure we only work with positive values.
  *************************************************************************************/
  int size(int a, int b)
  {
	  a < 0 ? a += 256 : a = a;
	  b < 0 ? b += 256 : b = b;	
	  return (a - 1) + (b - 1) * 256;
  }
 }

