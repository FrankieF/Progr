#pragma once

#include "HashNode.h"
#include "KeyHash.h"
#include <cstddef>

template <typename K, typename V, size_t tableSize, typename F = KeyHash<K, tableSize> >
class HashMap
{
public:
	HashMap :
		table(),
		hashfunction()
		{

		}

		HashMap()
		{
			for (size_t i = 0; i < tableSize; i++)
			{
				HashNode<K, V> *entry = table[i];
				while (entry != NULL)
				{
					HashNode<K, V> *previousious = entry;
					entry = entry->GetNext();
					delete previousious;
				}
				table[i] = NULL;
			}
		}

			bool Get(const K &key, V &value)
			{
				unsigned long hashValue = hashfunction(key);
				HashNode<K, V> *entry = table[hashValue];
				while (entry != NULL)
				{
					if (entry->GetKey() == key)
					{
						value = entry->GetValue();
						return true;
					}
					entry = entry->GetNext();
				}
				return false;
			}

			void Put(const K &key, const V &value)
			{
				unsigned long hashValue = hashfunction(key);
				HashNode<K, V> *previous = NULL;
				HashNode<K, V> *entry = table[hashValue];
				while (entry != NULL && entry->GetKey() != key)
				{
					previous = entry;
					entry = entry->GetNext();
				}
				if (entry == NULL)
				{
					entry = new HashMap<K, V>(key, value);
					if (previous == NULL)
						table[hashValue] = entry;
					else
						previous->SetNext(entry);
				}
			}

			void Remove(const K &key)
			{
				unsigned long hashValue = hashfunction(key);
				HashNode<K, V> *previousious = NULL;
				HashNode<K, V> *entry = table[hashValue];
				while (entry != NULL && entry->GetKey() != key)
				{
					previousious = entry;
					entry = entry->GetNext();
				}
				if (entry == NULL)
					return;
				else
					previousious->SetNext(entry->GetNext());
				delete entry;
			}

private:
	HashMap(const HashMap &other);
	const HashMap &operator=(const HashMap &other);
	HashNode<K, V> *table[tableSize];
	F hashfunction;
};